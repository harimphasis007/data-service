package com.data.service.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.data.service.web.rest.TestUtil;

public class WorkerHistoryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkerHistory.class);
        WorkerHistory workerHistory1 = new WorkerHistory();
        workerHistory1.setId(1L);
        WorkerHistory workerHistory2 = new WorkerHistory();
        workerHistory2.setId(workerHistory1.getId());
        assertThat(workerHistory1).isEqualTo(workerHistory2);
        workerHistory2.setId(2L);
        assertThat(workerHistory1).isNotEqualTo(workerHistory2);
        workerHistory1.setId(null);
        assertThat(workerHistory1).isNotEqualTo(workerHistory2);
    }
}
