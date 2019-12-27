package com.data.service.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.data.service.web.rest.TestUtil;

public class CommitmentTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Commitment.class);
        Commitment commitment1 = new Commitment();
        commitment1.setId(1L);
        Commitment commitment2 = new Commitment();
        commitment2.setId(commitment1.getId());
        assertThat(commitment1).isEqualTo(commitment2);
        commitment2.setId(2L);
        assertThat(commitment1).isNotEqualTo(commitment2);
        commitment1.setId(null);
        assertThat(commitment1).isNotEqualTo(commitment2);
    }
}
