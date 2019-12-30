package com.data.service.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.data.service.web.rest.TestUtil;

public class ProjectLogTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectLog.class);
        ProjectLog projectLog1 = new ProjectLog();
        projectLog1.setId(1L);
        ProjectLog projectLog2 = new ProjectLog();
        projectLog2.setId(projectLog1.getId());
        assertThat(projectLog1).isEqualTo(projectLog2);
        projectLog2.setId(2L);
        assertThat(projectLog1).isNotEqualTo(projectLog2);
        projectLog1.setId(null);
        assertThat(projectLog1).isNotEqualTo(projectLog2);
    }
}
