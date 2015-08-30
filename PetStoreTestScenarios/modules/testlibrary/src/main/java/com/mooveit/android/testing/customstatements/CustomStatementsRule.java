package com.mooveit.android.testing.customstatements;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.MultipleFailureException;
import org.junit.runners.model.Statement;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CustomStatementsRule implements TestRule {

    private List<MethodStatement> defaultBeforeStatements;
    private List<MethodStatement> defaultAfterStatements;
    private List<MethodStatement> statements;

    private Object test;
    private String runnerId;

    public CustomStatementsRule(Object test) {
        this(test, null);
    }

    public CustomStatementsRule(Object test, String runnerId) {
        this.test = test;
        this.runnerId = runnerId;

        this.defaultBeforeStatements = findStatements(
                com.mooveit.android.testing.customstatements.BeforeStatements.class,
                test,
                runnerId
        );

        this.statements = findStatements(
                com.mooveit.android.testing.customstatements.MethodStatement.class,
                test,
                runnerId
        );

        statements.addAll(defaultBeforeStatements);

        this.defaultAfterStatements = findStatements(
                AfterStatements.class,
                test,
                runnerId
        );
    }

    @Override
    public Statement apply(Statement statement, Description description) {
        try {
            RunWithStatements runWithStatements =
                    getRunWithStatementsFromMethod(test, description.getMethodName());

            if (runWithStatements == null) {
                runWithStatements = new DefaultRunWithStatements();
            }

            return createStatementsRunWithStatements(statement, runWithStatements);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw exception;
        }
    }

    private AfterBeforeStatement createStatementsRunWithStatements(
            Statement statement, RunWithStatements runWithStatements) {

        List<MethodStatement> filteredBeforeStatements = filterMethodStatementsByRunOption(
                defaultBeforeStatements, runWithStatements.runDefaults()
        );

        filteredBeforeStatements.addAll(
                filterMethodStatementsByNames(
                        statements, Arrays.asList(runWithStatements.before())
                )
        );

        List<MethodStatement> filteredAfterStatements = filterMethodStatementsByRunOption(
                defaultAfterStatements, runWithStatements.runDefaults()
        );

        filteredAfterStatements.addAll(
                filterMethodStatementsByNames(
                        statements, Arrays.asList(runWithStatements.after())
                )
        );

        return new AfterBeforeStatement(
                statement, filteredBeforeStatements, filteredAfterStatements
        );
    }

    private static List<MethodStatement> filterMethodStatementsByRunOption(
            List<MethodStatement> statements, boolean runAll) {

        Collections.sort(statements);

        if (runAll) {
            return statements;
        }

        List<MethodStatement> filteredStatements = new ArrayList<>();

        for (MethodStatement statement : statements) {
            if (statement.runAlways()) {
                filteredStatements.add(statement);
            }
        }

        return filteredStatements;
    }

    private static List<MethodStatement> filterMethodStatementsByNames(
            List<MethodStatement> statements, List<String> statementsNames) {

        List<MethodStatement> filteredStatements = new ArrayList<>();

        for (String statementName : statementsNames) {
            MethodStatement statement = findStatementByName(statements, statementName);
            if (statement != null) {
                filteredStatements.add(statement);
            }
        }

        return filteredStatements;
    }

    private static MethodStatement findStatementByName(List<MethodStatement> statements,
                                                       String name) {

        for (MethodStatement statement : statements) {
            if (name.equals(statement.methodName())) {
                return statement;
            }
        }

        throw new RuntimeException(
                new NoSuchMethodException(name)
        );
    }

    private static RunWithStatements getRunWithStatementsFromMethod(Object holder, String methodName) {
        Method method = getMethodByNameSafety(methodName, holder.getClass());

        if (method == null) {
            return null;
        }

        return method.getAnnotation(RunWithStatements.class);
    }

    private static Method getMethodByNameSafety(String methodName, Class<?> holderClass) {
        try {
            return holderClass.getMethod(methodName);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<MethodStatement> findStatements(Class<? extends Annotation> annotationClass,
                                                  Object test, String runnerId) {

        List<MethodStatement> statements = new ArrayList<>();

        for (Method method : test.getClass().getMethods()) {
            Annotation annotation = method.getAnnotation(annotationClass);

            if (annotation != null && hasRightId(annotation, runnerId)) {
                statements.add(
                        new MethodStatement(
                                method,
                                test,
                                nameFor(annotation),
                                shouldRunAlways(annotation)
                        )
                );
            }
        }

        return statements;
    }

    private static String nameFor(Annotation annotation) {
        if (annotation instanceof com.mooveit.android.testing.customstatements.MethodStatement) {
            return ((com.mooveit.android.testing.customstatements.MethodStatement) annotation).
                    value();
        }
        return null;
    }

    private static boolean shouldRunAlways(Annotation annotation) {
        if (annotation instanceof BeforeStatements) {
            return ((BeforeStatements) annotation).runAlways();
        } else if (annotation instanceof AfterStatements) {
            return ((AfterStatements) annotation).runAlways();
        }
        return true;
    }

    private static boolean hasRightId(Annotation annotation, String runnerId) {
        String[] runnersId = null;

        if (annotation instanceof BeforeStatements) {
            runnersId = ((BeforeStatements) annotation).runnersId();
        } else if (annotation instanceof AfterStatements) {
            runnersId = ((AfterStatements) annotation).runnersId();
        } else if (annotation instanceof MethodStatement) {
            runnersId = ((com.mooveit.android.testing.customstatements.MethodStatement) annotation).runnersId();
        }

        return runnersId == null ||
                (runnersId.length == 0 && runnerId == null) ||
                Arrays.binarySearch(runnersId, runnerId) >= 0;
    }

    private static class MethodStatement extends Statement implements Comparable<MethodStatement> {

        private Method method;
        private Object target;
        private String name;
        private boolean runAlways;

        public MethodStatement(Method method, Object target, String name, boolean runAlways) {
            this.method = method;
            this.target = target;
            this.name = name;
            this.runAlways = runAlways;
        }

        @Override
        public void evaluate() throws Throwable {
            try {
                method.invoke(target);
            } catch (Exception ex) {
                throw ex.getCause();
            }
        }

        String methodName() {
            return (name != null && !name.equals("")) ? name : method.getName();
        }

        boolean runAlways() {
            return runAlways;
        }

        @Override
        public int compareTo(MethodStatement another) {
            if (this == another) {
                return 0;
            } else if (runAlways && another.runAlways || !runAlways) {
                return 1;
            } else {
                return -1;
            }
        }
    }

    private static class AfterBeforeStatement extends Statement {

        private List<? extends Statement> beforeStatements;
        private List<? extends Statement> afterStatements;
        private Statement statement;

        public AfterBeforeStatement(Statement statement, List<? extends Statement> beforeStatements,
                               List<? extends Statement> afterStatements) {
            this.beforeStatements = beforeStatements;
            this.afterStatements = afterStatements;
            this.statement = statement;
        }

        @Override
        public void evaluate() throws Throwable {
            List<Throwable> errors = new ArrayList<>();

            errors.addAll(runStatements(beforeStatements));

            Throwable testError = null;

            if (errors.isEmpty()) {
                testError = runStatementSafety(statement);
            }

            if (testError != null) {
                errors.add(testError);
            }

            errors.addAll(runStatements(afterStatements));

            MultipleFailureException.assertEmpty(errors);
        }

        private static List<Throwable> runStatements(List<? extends Statement> statements) {
            List<Throwable> errors = new ArrayList<>();

            for (Statement statement : statements) {
                Throwable error = runStatementSafety(statement);
                if (error != null) {
                    errors.add(error);
                }
            }

            return errors;
        }

        private static Throwable runStatementSafety(Statement statement) {
            try {
                statement.evaluate();
            } catch (Throwable error) {
                return error;
            }

            return null;
        }
    }

    private static class DefaultRunWithStatements implements RunWithStatements {

        @Override
        public String[] before() {
            return new String[0];
        }

        @Override
        public String[] after() {
            return new String[0];
        }

        @Override
        public boolean runDefaults() {
            return true;
        }

        @Override
        public Class<? extends Annotation> annotationType() {
            return RunWithStatements.class;
        }

    }
}
