package com.mooveit.petstoretestscenarios.networking.requests;

import android.content.Context;

import com.mooveit.android.dependencies.ContextAware;
import com.mooveit.android.dependencies.RestTemplateAware;
import com.mooveit.android.networking.requests.BaseRequest;
import com.mooveit.petstoretestscenarios.R;
import com.octo.android.robospice.request.SpiceRequest;

import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public abstract class PetStoreRequest<RESULT_TYPE, BODY_TYPE>
        extends SpiceRequest<RESULT_TYPE>
        implements ContextAware, RestTemplateAware {

    private BaseRequest<RESULT_TYPE, BODY_TYPE> mRequest;

    public PetStoreRequest(Class<RESULT_TYPE> resultTypeClass) {
        super(resultTypeClass);

        mRequest = new BaseRequest<>(resultTypeClass);
        mRequest.getHeaders().setContentType(MediaType.APPLICATION_JSON);
    }

    @Override
    public final RESULT_TYPE loadDataFromNetwork() throws Exception {
        onPrepareRequest();
        return processResult(mRequest.loadDataFromNetwork());
    }

    public RESULT_TYPE processResult(RESULT_TYPE result) {
        return result;
    }

    public void onPrepareRequest() {
    }

    @Override
    public void setContext(Context context) {
        mRequest.setBaseRoute(context.getString(R.string.api_path));
    }

    @Override
    public void setRestTemplate(RestTemplate restTemplate) {
        mRequest.setRestTemplate(restTemplate);
    }

    public BaseRequest<RESULT_TYPE, BODY_TYPE> getRequest() {
        return mRequest;
    }
}
