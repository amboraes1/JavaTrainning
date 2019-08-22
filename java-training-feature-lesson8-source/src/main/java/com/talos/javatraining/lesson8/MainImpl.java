package com.talos.javatraining.lesson8;



import java.time.*;
import java.time.temporal.TemporalAdjuster;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;


public class MainImpl implements Main
{

	@Override
	public Instant getInstant(String dateTime)
	{
		LocalDateTime zon = LocalDateTime.parse(dateTime);
		zon =zon.minusMinutes(10);
		zon =zon.plusSeconds(1);
		ZoneOffset off = ZoneOffset.of("-5");
		return zon.toInstant(off);
	}

	@Override
	public Duration getDuration(Instant a, Instant b)
	{
		Duration dur =Duration.between(a,b);
		dur = dur.plusDays(1);
		dur = dur.minusHours(4);
		return dur;
	}

	@Override
	public String getHumanReadableDate(LocalDateTime localDateTime)
	{
		localDateTime = localDateTime.plusHours(3);
		localDateTime =localDateTime.withMonth(7);
		if (localDateTime.getYear()%2==0){
			localDateTime =localDateTime.plusYears(1);
		}
		//localDateTime = localDateTime.plusYears(1);
		return localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE);
	}

	@Override
	public LocalDateTime getLocalDateTime(String dateTime)
	{
		DateTimeFormatter format = DateTimeFormatter.ofPattern("ssmmHHddMMyyyy");
		LocalDateTime tim = LocalDateTime.parse(dateTime,format);
		if (tim.getMonthValue()%2!=0){
			tim = tim.plusMonths(1);
		}
		int seg = tim.getSecond();
		if(seg * 2 >= 60){
			seg = seg*2 - 60;
			tim = tim.minusSeconds(tim.getSecond());
			tim = tim.plusSeconds(seg);
			tim = tim.plusMinutes(1);
		}else{
			tim =tim.plusSeconds(seg);
		}
		return tim;
	}

	@Override
	public Period calculateNewPeriod(Period period)
	{
		period = period.plusMonths(5);
		period = period.plusDays(6);
		period = period.minusDays(14);
		return period;
	}

	@Override
	public LocalDate toLocalDate(Year year, MonthDay monthDay)
	{
		LocalDate local = year.atMonthDay(monthDay);
		local = local.plusYears(3);

		int day = local.getDayOfMonth()%5;

		if (day != 0 && local.getDayOfMonth()>1){
			local =local.minusDays(day);
		}
		return local;
	}

	@Override
	public LocalDateTime toLocalDateTime(YearMonth yearMonth, int dayOfMonth, LocalTime time)
	{
		LocalDateTime local = LocalDateTime.of(yearMonth.getYear(),yearMonth.getMonthValue(),dayOfMonth,time.getHour(),time.getMinute(),time.getSecond(),time.getNano());
		local = local.withSecond(0);
		local = local.minusMinutes(37);
		local = local.plusDays(3);
		return local;
	}

	@Override
	public TemporalAdjuster createTemporalAdjusterNextMonday()
	{
		return TemporalAdjusters.next(DayOfWeek.MONDAY);
	}

	@Override
	public TemporalAdjuster createTemporalAdjusterNextFebruaryFirst()
	{
		return TemporalAdjusters.ofDateAdjuster(result->{
			if (result.getMonthValue() >= 2){
				result = result.withDayOfMonth(1);
				result = result.withMonth(2);
				result = result.plusYears(1);
				return result;
			}else {
				result = result.withMonth(2);
				result = result.withDayOfMonth(1);
				return result;
			}
		});
	}

	@Override
	public String adjustDateTime(String localDateTime, TemporalAdjuster adjuster)
	{
		return LocalDateTime.parse(localDateTime).with(adjuster).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
	}

	@Override
	public String processZonedDateTime(String zonedDateTime)
	{
		ZonedDateTime local = ZonedDateTime.parse(zonedDateTime,DateTimeFormatter.ISO_OFFSET_DATE_TIME);
		local = local.plusHours(1);
		if(local.getMinute()%15!=0){;
			local = local.withMinute(local.getMinute()-local.getMinute()%15);
		}
		ZoneId id = ZoneId.of("UTC");
		return local.withZoneSameInstant(id).format(DateTimeFormatter.RFC_1123_DATE_TIME);
	}
}
