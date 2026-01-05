package com.ems.common;

import com.ems.common.events.AttendanceEvent;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;

class AttendanceEventTest {

    @Test
    void recordShouldHoldValues() {

        AttendanceEvent e = new AttendanceEvent(
                5L, "OUT",
                LocalDate.now(),
                OffsetDateTime.now(),
                7.5
        );

        assertEquals("OUT", e.eventType());
        assertEquals(7.5, e.workedHours());
    }
}
