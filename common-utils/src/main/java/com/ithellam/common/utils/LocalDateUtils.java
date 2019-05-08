package com.ithellam.common.utils;

import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
public class LocalDateUtils {
    public static final DateTimeFormatter PRETTY_FORMATTER = DateTimeFormatter.ofPattern("YYYY-MM-dd h:mm a zzz");

    public static final ZoneId UTC = ZoneOffset.UTC;

    // Canonical name in TZDB
    public static final ZoneId US_CENTRAL = ZoneId.of("America/Chicago");

    public static final LocalDate MIN_LOCAL_DATE = LocalDate.of(2010, 1, 1);

    public static final LocalDate MAX_LOCAL_DATE = LocalDate.of(2999, 12, 31);

    private static final LocalTime MIN_LOCAL_TIME = LocalTime.of(0, 0, 0, 0);

    public static final ZonedDateTime MIN_ZONED_DATE_TIME = ZonedDateTime.of(MIN_LOCAL_DATE, MIN_LOCAL_TIME, UTC);

    public static final ZonedDateTime MAX_ZONED_DATE_TIME = ZonedDateTime.of(MAX_LOCAL_DATE, MIN_LOCAL_TIME,
            ZoneOffset.UTC);

    public static final Month LAST_MONTH_OF_PLANNING_YEAR = Month.MAY;

    /**
     * This method doesn't do any time zone conversion. UTC timezone info is added
     * to given localDateTime. Using 'Instant' instead of 'LocalDateTime' causes
     * some timezone conversion.
     *
     * @return equivalent ZoneDateTime in UTC
     */
    public static ZonedDateTime buildUTCZonedDateTime(final LocalDateTime localDateTime) {
        return localDateTime.atZone(UTC);
    }

    /**
     * This method doesn't do any time zone conversion. Given zonedDateTime is in
     * UTC and will be converted to Timestamp which can be used to query for data.
     * Data is stored in the database tables in UTC.
     *
     * @return equivalent Timestamp
     */
    public static Timestamp convertToTimestamp(final ZonedDateTime zonedDateTime) {
        return Timestamp.valueOf(zonedDateTime.toLocalDateTime());
    }

    /**
     * Will convert a SQL Timestamp (which is in a LocalDateTime format) into a
     * ZonedDate time in UTC
     *
     * @param timestamp
     * @return
     */
    public static ZonedDateTime convertFromTimestamp(final Timestamp timestamp) {
        return timestamp.toLocalDateTime().atZone(UTC);
    }

    public static String format(final ZonedDateTime zonedDateTime) {
        if (zonedDateTime != null) {
            return zonedDateTime.format(DateTimeFormatter.ISO_DATE_TIME);
        }

        return "";
    }

    public static String format(final LocalDate operatingDate) {
        if (operatingDate != null) {
            return operatingDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
        }

        return "";
    }

    /**
     * Returns {@code ZonedDateTime} for the same {@code Instant} as given input,
     * with zone set as UTC
     *
     * @param zonedDateTime
     * @return UTC-based ZonedDateTime
     */
    public static ZonedDateTime buildUTCZonedDateTime(final ZonedDateTime zonedDateTime) {
        return zonedDateTime.withZoneSameInstant(UTC);
    }

    /**
     * Returns {@code ZonedDateTime} for the same {@code Instant} as given input,
     * with zone set as CST
     *
     * @param zonedDateTime
     * @return CST-based ZonedDateTime
     */
    public static ZonedDateTime buildCSTZonedDateTime(final ZonedDateTime zonedDateTime) {
        return zonedDateTime.withZoneSameInstant(US_CENTRAL);
    }

    /**
     * Returns {@code ZonedDateTime} for the same {@code Instant} as given input,
     * with zone set as CST and time set to MIDNIGHT which indicates the start of
     * the operating date
     *
     * @param zonedDateTime
     * @return CST-based ZonedDateTime with time as MIDNIGHT
     */
    public static ZonedDateTime buildCSTOperatingDate(final ZonedDateTime zonedDateTime) {
        return buildCSTZonedDateTime(zonedDateTime).with(LocalTime.MIDNIGHT);
    }

    /**
     * Converts {@code LocalDate} to a {@code ZonedDateTime} by specifying midnight
     * US central time as the time
     */
    public static ZonedDateTime buildCSTZonedDateTime(final LocalDate localDate) {
        return ZonedDateTime.of(localDate, LocalTime.MIDNIGHT, US_CENTRAL);
    }

    /**
     * Converts {@code LocalDate} to a {@code ZonedDateTime} by specifying midnight
     * US central time as the time, then converting that to a UTC based
     * {@code ZonedDateTime}.
     */
    public static ZonedDateTime buildUTCZonedDateTime(final LocalDate localDate) {
        return ZonedDateTime.of(localDate, LocalTime.MIDNIGHT, US_CENTRAL).withZoneSameInstant(UTC);
    }

    public static ZonedDateTime buildUTCZonedDateTime(final LocalDate localDate, final LocalTime localTime) {
        return ZonedDateTime.of(localDate, localTime, US_CENTRAL).withZoneSameInstant(UTC);
    }

    /**
     * Converts the ZonedDateTime to a LocalDate by switching the timezone to
     * US/Central and then converting to a LocalDate
     */
    public static LocalDate buildCSTLocalDate(final ZonedDateTime operatingDate) {
        return operatingDate.withZoneSameInstant(US_CENTRAL).toLocalDate();
    }

    /**
     *
     * @param year
     * @param month
     * @return last day of the month (28/29/30/31) based on given year and month
     */
    private static int getLastDayOfMonth(final int year, final Month month) {
        return YearMonth.of(year, month).lengthOfMonth();
    }

    /**
     * PlanningYear runs from June 1st of a year to May 31st of the next year.
     *
     * If given date falls on a month between January and May (both exclusive),
     * planningYearNum will be the same as the calendar year number.
     *
     * Otherwise, planningYearNum will be the next calendar year number.
     *
     * @param cstZonedDateTime
     * @return corresponding planningYear
     */
    public static int getPlanningYearNum(final ZonedDateTime cstZonedDateTime) {
        return (cstZonedDateTime.getMonth().compareTo(LAST_MONTH_OF_PLANNING_YEAR) <= 0) ? cstZonedDateTime.getYear()
                : cstZonedDateTime.getYear() + 1;
    }

    /**
     *
     * @param cstZonedDateTime
     * @return last date of the operating month to which the given input belongs
     */
    public static ZonedDateTime getLastDateOfOperatingMonth(final ZonedDateTime cstZonedDateTime) {
        return cstZonedDateTime
                .withDayOfMonth(getLastDayOfMonth(cstZonedDateTime.getYear(), cstZonedDateTime.getMonth()));
    }

    /**
     * PlanningYear runs from June 1st of a year to May 31st of the next year.
     *
     * @param cstZonedDateTime
     * @return last date of the planning year to which the given input belongs
     */
    public static ZonedDateTime getLastDateOfPlanningYear(final ZonedDateTime cstZonedDateTime) {
        final int planningYear = getPlanningYearNum(cstZonedDateTime);

        final LocalDate lastDateOfPlanningYear = LocalDate.of(planningYear, LAST_MONTH_OF_PLANNING_YEAR,
                getLastDayOfMonth(planningYear, LAST_MONTH_OF_PLANNING_YEAR));

        return buildCSTZonedDateTime(lastDateOfPlanningYear);
    }

    public static boolean isMidnightUsCentral(final ZonedDateTime zonedDateTime) {
        final ZonedDateTime midnightUsCentralSameDate = buildUTCZonedDateTime(buildCSTLocalDate(zonedDateTime));
        return midnightUsCentralSameDate.equals(zonedDateTime);
    }

    /**
     * Returns the OperatingDate of the given zonedDateTime in CST.
     */
    public static ZonedDateTime getCSTOperatingDate(final ZonedDateTime zonedDateTime) {
        return zonedDateTime.withZoneSameInstant(US_CENTRAL).toLocalDate().atStartOfDay(US_CENTRAL);
    }

    /**
     * @return Temporal Adjuster which will return the next weekday if temporal
     *         being adjusted isn't already a weekday
     */
    public static TemporalAdjuster nextOrSameWeekDay() {
        return (temporal) -> {
            final LocalDate input = LocalDate.from(temporal);
            if (input.getDayOfWeek() == DayOfWeek.SATURDAY || input.getDayOfWeek() == DayOfWeek.SUNDAY) {
                return input.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
            }
            return input;
        };
    }

    /**
     * @return Temporal Adjuster which will return the previous weekday if temporal
     *         being adjusted isn't already a weekday
     */
    public static TemporalAdjuster previousOrSameWeekDay() {
        return (temporal) -> {
            final LocalDate input = LocalDate.from(temporal);
            if (input.getDayOfWeek() == DayOfWeek.SATURDAY || input.getDayOfWeek() == DayOfWeek.SUNDAY) {
                return input.with(TemporalAdjusters.previous(DayOfWeek.FRIDAY));
            }
            return input;
        };
    }

    /**
     * @return Temporal Adjuster which will return the posting day of the daily
     *         transmission OD
     */
    public static TemporalAdjuster transmissionDailyOperatingDayToPostingDay() {
        return (temporal) -> {
            final LocalDate input = LocalDate.from(temporal);

            LocalDate operatingDay = input.with(TemporalAdjusters.lastDayOfMonth());

            // Postings start on the 2nd week day of the next month, for the 3 previous
            // daily's posting.
            LocalDate postingDate = input.plusMonths(1).with(weekDayOfMonth(2));

            for (int i = 0; i < 3; ++i) {
                if (operatingDay.equals(input)) {
                    return postingDate;
                }

                operatingDay = operatingDay.minusDays(1);
            }

            postingDate = postingDate.minusDays(1).with(previousOrSameWeekDay());

            // Now post 2 ODs per week day
            int count = 0;
            while (operatingDay.getMonth() == input.getMonth()) {
                if (operatingDay.equals(input)) {
                    return postingDate;
                }

                ++count;
                if (count != 0 && count % 2 == 0) {
                    postingDate = postingDate.minusDays(1).with(previousOrSameWeekDay());
                }

                operatingDay = operatingDay.minusDays(1);
            }

            throw new IllegalStateException(
                    "Unable to find transmission daily posting day for operating day: " + input);
        };
    }

    public static TemporalAdjuster weekDayOfMonth(final int count) {
        return (temporal) -> {
            final LocalDate input = LocalDate.from(temporal);
            LocalDate weekDay = input.with(TemporalAdjusters.firstDayOfMonth()).with(nextOrSameWeekDay());
            for (int i = 1; i < count; ++i) {
                weekDay = weekDay.plusDays(1).with(nextOrSameWeekDay());
                if (weekDay.getMonth() != input.getMonth()) {
                    throw new IllegalArgumentException(
                            "Could not find week day number " + count + " of " + input.getMonth());
                }
            }

            return weekDay;
        };
    }
}
