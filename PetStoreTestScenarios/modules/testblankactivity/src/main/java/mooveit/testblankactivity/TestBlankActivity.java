package mooveit.testblankactivity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class TestBlankActivity extends FragmentActivity {

    public static final int VIEW_ID = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout frameLayout = new FrameLayout(this);
        frameLayout.setLayoutParams(
                new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
                )
        );
        frameLayout.setBackgroundColor(Color.WHITE);
        frameLayout.setId(VIEW_ID);
        setContentView(frameLayout);
    }
}
