package com.data.service.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.data.service.web.rest.TestUtil;

public class DrawdownHistoryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DrawdownHistory.class);
        DrawdownHistory drawdownHistory1 = new DrawdownHistory();
        drawdownHistory1.setId(1L);
        DrawdownHistory drawdownHistory2 = new DrawdownHistory();
        drawdownHistory2.setId(drawdownHistory1.getId());
        assertThat(drawdownHistory1).isEqualTo(drawdownHistory2);
        drawdownHistory2.setId(2L);
        assertThat(drawdownHistory1).isNotEqualTo(drawdownHistory2);
        drawdownHistory1.setId(null);
        assertThat(drawdownHistory1).isNotEqualTo(drawdownHistory2);
    }
}
