package com.noefer.pontoeletronicoapi;

import com.noefer.pontoeletronicoapi.model.*;
import com.noefer.pontoeletronicoapi.model.dto.WorkDayReport;
import com.noefer.pontoeletronicoapi.service.WorkDayService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
public class WorkDayServiceTests {
    @Autowired
    private WorkDayService service;

    //https://www.calculator.net/time-duration-calculator.html

    @Test
    void testWorkDayWithCompleteShift() {
        WorkDay workDay = new WorkDay();

        UserProfile user = new UserProfile();
        user.setWorkLoad(WorkLoad.SIXHOURS);

        workDay.setUser(user);
        workDay.setId(1L);

        List<TimeRecord> timeRecords = Arrays.asList(
                new TimeRecord(1L, LocalDateTime.of(2023, 1, 1, 8, 0, 0), TimeRecordType.CHECKIN, workDay),
                new TimeRecord(2L, LocalDateTime.of(2023, 1, 1, 12, 0, 0), TimeRecordType.BREAK, workDay),
                new TimeRecord(3L, LocalDateTime.of(2023, 1, 1, 13, 0, 0), TimeRecordType.RESUME, workDay),
                new TimeRecord(4L, LocalDateTime.of(2023, 1, 1, 17, 0, 0), TimeRecordType.CHECKOUT, workDay)
        );
        workDay.setTimeRecords(timeRecords);

        WorkDayReport info = service.generateWorkDayReport(workDay, Duration.ofHours(workDay.getUser().getWorkLoad().getHours()));

        Assertions.assertEquals(28800, info.getWorkedTime());
        Assertions.assertEquals(0, info.getRemainingTime());
        Assertions.assertEquals(7200, info.getExceededTime());
    }

    @Test
    void testWorkDayWithMultipleBreaksIncludingMinutesAndSeconds() {
        WorkDay workDay = new WorkDay();

        UserProfile user = new UserProfile();
        user.setWorkLoad(WorkLoad.SIXHOURS);

        workDay.setUser(user);
        workDay.setId(6L);

        List<TimeRecord> timeRecords = Arrays.asList(
                new TimeRecord(1L, LocalDateTime.of(2024, 1, 1, 8, 15, 30), TimeRecordType.CHECKIN, workDay),
                new TimeRecord(2L, LocalDateTime.of(2024, 1, 1, 10, 45, 15), TimeRecordType.BREAK, workDay),
                new TimeRecord(3L, LocalDateTime.of(2024, 1, 1, 11, 15, 45), TimeRecordType.RESUME, workDay),
                new TimeRecord(4L, LocalDateTime.of(2024, 1, 1, 12, 50, 30), TimeRecordType.BREAK, workDay),
                new TimeRecord(5L, LocalDateTime.of(2024, 1, 1, 13, 20, 15), TimeRecordType.RESUME, workDay),
                new TimeRecord(6L, LocalDateTime.of(2024, 1, 1, 15, 10, 45), TimeRecordType.CHECKOUT, workDay)
        );
        workDay.setTimeRecords(timeRecords);

        WorkDayReport info = service.generateWorkDayReport(workDay, Duration.ofHours(workDay.getUser().getWorkLoad().getHours()));

        Assertions.assertEquals(21300, info.getWorkedTime());
        Assertions.assertEquals(300, info.getRemainingTime());
        Assertions.assertEquals(0, info.getExceededTime());
    }

    @Test
    void testWorkDayAcrossTwoDaysWithSeconds() {
        WorkDay workDay = new WorkDay();

        UserProfile user = new UserProfile();
        user.setWorkLoad(WorkLoad.SIXHOURS);

        workDay.setUser(user);
        workDay.setId(7L);

        List<TimeRecord> timeRecords = Arrays.asList(
                new TimeRecord(1L, LocalDateTime.of(2024, 1, 1, 22, 30, 0), TimeRecordType.CHECKIN, workDay),
                new TimeRecord(2L, LocalDateTime.of(2024, 1, 2, 2, 45, 30), TimeRecordType.BREAK, workDay),
                new TimeRecord(3L, LocalDateTime.of(2024, 1, 2, 3, 10, 15), TimeRecordType.RESUME, workDay),
                new TimeRecord(4L, LocalDateTime.of(2024, 1, 2, 6, 15, 45), TimeRecordType.CHECKOUT, workDay)
        );
        workDay.setTimeRecords(timeRecords);

        WorkDayReport info = service.generateWorkDayReport(workDay, Duration.ofHours(workDay.getUser().getWorkLoad().getHours()));

        Assertions.assertEquals(26460, info.getWorkedTime());
        Assertions.assertEquals(0, info.getRemainingTime());
        Assertions.assertEquals(4860, info.getExceededTime());
    }

    @Test
    void testIncompleteWorkDayWithSeconds() {
        WorkDay workDay = new WorkDay();

        UserProfile user = new UserProfile();
        user.setWorkLoad(WorkLoad.SIXHOURS);

        workDay.setUser(user);
        workDay.setId(8L);

        List<TimeRecord> timeRecords = Arrays.asList(
                new TimeRecord(1L, LocalDateTime.of(2024, 1, 1, 8, 0, 15), TimeRecordType.CHECKIN, workDay),
                new TimeRecord(2L, LocalDateTime.of(2024, 1, 1, 12, 30, 45), TimeRecordType.BREAK, workDay)
        );
        workDay.setTimeRecords(timeRecords);

        WorkDayReport info = service.generateWorkDayReport(workDay, Duration.ofHours(workDay.getUser().getWorkLoad().getHours()));

        Assertions.assertEquals(16230, info.getWorkedTime());
        Assertions.assertEquals(5370, info.getRemainingTime());
        Assertions.assertEquals(0, info.getExceededTime());
    }

    @Test
    void testWorkDayWithNoBreaksIncludingSeconds() {
        WorkDay workDay = new WorkDay();

        UserProfile user = new UserProfile();
        user.setWorkLoad(WorkLoad.SIXHOURS);

        workDay.setUser(user);
        workDay.setId(9L);

        List<TimeRecord> timeRecords = Arrays.asList(
                new TimeRecord(1L, LocalDateTime.of(2024, 1, 1, 8, 0, 15), TimeRecordType.CHECKIN, workDay),
                new TimeRecord(2L, LocalDateTime.of(2024, 1, 1, 14, 10, 45), TimeRecordType.CHECKOUT, workDay)
        );
        workDay.setTimeRecords(timeRecords);

        WorkDayReport info = service.generateWorkDayReport(workDay, Duration.ofHours(workDay.getUser().getWorkLoad().getHours()));

        Assertions.assertEquals(22230, info.getWorkedTime());
        Assertions.assertEquals(0, info.getRemainingTime());
        Assertions.assertEquals(630, info.getExceededTime());
    }

    @Test
    void testWorkDayWithBreaksAndSeconds8Hours() {
        WorkDay workDay = new WorkDay();

        UserProfile user = new UserProfile();
        user.setWorkLoad(WorkLoad.EIGHTHOURS);

        workDay.setUser(user);
        workDay.setId(14L);

        List<TimeRecord> timeRecords = Arrays.asList(
                new TimeRecord(1L, LocalDateTime.of(2024, 1, 1, 8, 15, 30), TimeRecordType.CHECKIN, workDay),
                new TimeRecord(2L, LocalDateTime.of(2024, 1, 1, 12, 45, 50), TimeRecordType.BREAK, workDay),
                new TimeRecord(3L, LocalDateTime.of(2024, 1, 1, 13, 30, 15), TimeRecordType.RESUME, workDay),
                new TimeRecord(4L, LocalDateTime.of(2024, 1, 1, 17, 5, 45), TimeRecordType.CHECKOUT, workDay)
        );
        workDay.setTimeRecords(timeRecords);

        WorkDayReport info = service.generateWorkDayReport(workDay, Duration.ofHours(workDay.getUser().getWorkLoad().getHours()));

        Assertions.assertEquals(29150, info.getWorkedTime());
        Assertions.assertEquals(0, info.getRemainingTime());
        Assertions.assertEquals(350, info.getExceededTime());
    }

    @Test
    void testWorkDayAcrossTwoDaysWithSeconds8Hours() {
        WorkDay workDay = new WorkDay();

        UserProfile user = new UserProfile();
        user.setWorkLoad(WorkLoad.EIGHTHOURS);

        workDay.setUser(user);
        workDay.setId(15L);

        List<TimeRecord> timeRecords = Arrays.asList(
                new TimeRecord(1L, LocalDateTime.of(2024, 1, 1, 23, 59, 50), TimeRecordType.CHECKIN, workDay),
                new TimeRecord(2L, LocalDateTime.of(2024, 1, 2, 3, 15, 40), TimeRecordType.BREAK, workDay),
                new TimeRecord(3L, LocalDateTime.of(2024, 1, 2, 4, 10, 30), TimeRecordType.RESUME, workDay),
                new TimeRecord(4L, LocalDateTime.of(2024, 1, 2, 8, 15, 10), TimeRecordType.CHECKOUT, workDay)
        );
        workDay.setTimeRecords(timeRecords);

        WorkDayReport info = service.generateWorkDayReport(workDay, Duration.ofHours(workDay.getUser().getWorkLoad().getHours()));

        Assertions.assertEquals(26430, info.getWorkedTime());
        Assertions.assertEquals(2370, info.getRemainingTime());
        Assertions.assertEquals(0, info.getExceededTime());
    }

    @Test
    void testIncompleteWorkDayWithSeconds8Hours() {
        WorkDay workDay = new WorkDay();

        UserProfile user = new UserProfile();
        user.setWorkLoad(WorkLoad.EIGHTHOURS);

        workDay.setUser(user);
        workDay.setId(16L);

        List<TimeRecord> timeRecords = Arrays.asList(
                new TimeRecord(1L, LocalDateTime.of(2024, 1, 1, 9, 20, 10), TimeRecordType.CHECKIN, workDay),
                new TimeRecord(2L, LocalDateTime.of(2024, 1, 1, 13, 45, 30), TimeRecordType.BREAK, workDay)
        );
        workDay.setTimeRecords(timeRecords);

        WorkDayReport info = service.generateWorkDayReport(workDay, Duration.ofHours(workDay.getUser().getWorkLoad().getHours()));

        Assertions.assertEquals(15920, info.getWorkedTime());
        Assertions.assertEquals(12880, info.getRemainingTime());
        Assertions.assertEquals(0, info.getExceededTime());
    }

    @Test
    void testWorkDayWithNoBreaksAndSeconds8Hours() {
        WorkDay workDay = new WorkDay();

        UserProfile user = new UserProfile();
        user.setWorkLoad(WorkLoad.EIGHTHOURS);

        workDay.setUser(user);
        workDay.setId(17L);

        List<TimeRecord> timeRecords = Arrays.asList(
                new TimeRecord(1L, LocalDateTime.of(2024, 1, 1, 8, 5, 45), TimeRecordType.CHECKIN, workDay),
                new TimeRecord(2L, LocalDateTime.of(2024, 1, 1, 16, 35, 15), TimeRecordType.CHECKOUT, workDay)
        );
        workDay.setTimeRecords(timeRecords);

        WorkDayReport info = service.generateWorkDayReport(workDay, Duration.ofHours(workDay.getUser().getWorkLoad().getHours()));

        Assertions.assertEquals(30570, info.getWorkedTime());
        Assertions.assertEquals(0, info.getRemainingTime());
        Assertions.assertEquals(1770, info.getExceededTime());
    }

}
