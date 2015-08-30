package com.mooveit.petstoretestscenarios.networking.requests;

import com.mooveit.petstoretestscenarios.networking.entities.Pet;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.util.UriComponentsBuilder;

public class PetsByStatusRequest extends PetStoreRequest<Pet[], Object> {

    private static final String PATH = "/pet/findByStatus";
    private static final String STATUS_KEY = "status";
    private static final String PARAMS_ARRAY_SEPARATOR = ",";

    public PetsByStatusRequest() {
        super(Pet[].class);

        setRetryPolicy(null);
    }

    @Override
    public void onPrepareRequest() {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
        uriComponentsBuilder.queryParam(STATUS_KEY, StringUtils.join(
                Pet.Status.statusNames(), PARAMS_ARRAY_SEPARATOR
        ));

        getRequest().setPath(PATH);
        getRequest().setParams(
                "?" + uriComponentsBuilder.build().getQuery(),
                "?" + uriComponentsBuilder.build(true).getQuery()
        );
    }
}
