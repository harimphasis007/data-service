package com.data.service.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.data.service.web.rest.TestUtil;

public class LoanpoolTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Loanpool.class);
        Loanpool loanpool1 = new Loanpool();
        loanpool1.setId(1L);
        Loanpool loanpool2 = new Loanpool();
        loanpool2.setId(loanpool1.getId());
        assertThat(loanpool1).isEqualTo(loanpool2);
        loanpool2.setId(2L);
        assertThat(loanpool1).isNotEqualTo(loanpool2);
        loanpool1.setId(null);
        assertThat(loanpool1).isNotEqualTo(loanpool2);
    }
}
