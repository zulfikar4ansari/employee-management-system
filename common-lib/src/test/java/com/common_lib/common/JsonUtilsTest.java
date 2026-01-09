package com.common_lib.common;

import com.common_lib.events.AttendanceEvent;
import com.common_lib.utils.JsonUtils;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;

class JsonUtilsTest {

    @Test
    void shouldSerializeAndDeserializeAttendanceEvent() {

        AttendanceEvent event = new AttendanceEvent(
                10L, "IN", LocalDate.now(), OffsetDateTime.now(), null
        );

        String json = JsonUtils.toJson(event);

        assertNotNull(json);

        AttendanceEvent result =
                JsonUtils.fromJson(json, AttendanceEvent.class);

        assertEquals(event.employeeId(), result.employeeId());
        assertEquals(event.eventType(), result.eventType());
    }
}
