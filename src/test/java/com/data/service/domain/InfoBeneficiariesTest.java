package com.data.service.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.data.service.web.rest.TestUtil;

public class InfoBeneficiariesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InfoBeneficiaries.class);
        InfoBeneficiaries infoBeneficiaries1 = new InfoBeneficiaries();
        infoBeneficiaries1.setId(1L);
        InfoBeneficiaries infoBeneficiaries2 = new InfoBeneficiaries();
        infoBeneficiaries2.setId(infoBeneficiaries1.getId());
        assertThat(infoBeneficiaries1).isEqualTo(infoBeneficiaries2);
        infoBeneficiaries2.setId(2L);
        assertThat(infoBeneficiaries1).isNotEqualTo(infoBeneficiaries2);
        infoBeneficiaries1.setId(null);
        assertThat(infoBeneficiaries1).isNotEqualTo(infoBeneficiaries2);
    }
}
