/**
 * ****************************************************************************
 * Copyright (c) 2015 John Wiley & Sons, Inc. All rights reserved.
 * <p>
 * All material contained herein is proprietary to John Wiley & Sons
 * and its third party suppliers, if any. The methods, techniques and
 * technical concepts contained herein are considered trade secrets
 * and confidential and may be protected by intellectual property laws.
 * Reproduction or distribution of this material, in whole or in part,
 * is strictly forbidden except by express prior written permission
 * of John Wiley & Sons.
 * *****************************************************************************
 */
package com.wiley.gr.ace.discount.model;

import java.util.List;

/**
 * Created by KKALYAN on 8/12/2015.
 */
public class GetInstitutionsResponse {

    private List<GetInstitutionResponse> institutions;

    public List<GetInstitutionResponse> getInstitutions() {
        return institutions;
    }

    public void setInstitutions(List<GetInstitutionResponse> institutions) {
        this.institutions = institutions;
    }
}
