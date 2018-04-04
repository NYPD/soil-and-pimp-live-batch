package dao;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import configuration.EmbeddedDateSourceConfiguration;
import configuration.JvcJsonWebEventDaoConfiguration;
import live.soilandpimp.batch.configuration.BatchConfiguration;
import live.soilandpimp.batch.dao.JvcJsonWebEventDao;
import live.soilandpimp.batch.domain.Event;
import live.soilandpimp.batch.domain.Schedule;
import live.soilandpimp.batch.util.AppConstants;

@RunWith(SpringRunner.class)
@ActiveProfiles(AppConstants.TEST_PROFILE)
@ContextConfiguration(classes = {BatchConfiguration.class, EmbeddedDateSourceConfiguration.class,
                                 JvcJsonWebEventDaoConfiguration.class})
public class JvcJsonWebEventDaoTest {

    @Autowired
    private JvcJsonWebEventDao webEventDao;

    @Test
    public void shouldGetAllEvents() {
        List<Event> currentPostedEvents = webEventDao.getCurrentPostedEvents();

        assertThat(currentPostedEvents.size(), is(4));
    }

    @Test
    public void shouldPopulateEventObjectCorrectly() {
        List<Event> currentPostedEvents = webEventDao.getCurrentPostedEvents();

        Event saisonCardEvent = null;

        for (Event event : currentPostedEvents) {

            if (!"THE GREAT  SATSUMANIAN HESTIVAL2018".equals(event.getName()))
                continue;

            saisonCardEvent = event;
            break;

        }

        assertNotNull(saisonCardEvent);

        String eventKey = saisonCardEvent.getEventKey();
        String eventUrl = saisonCardEvent.getEventUrl();
        String jvcUrl = saisonCardEvent.getJvcUrl();
        String memo = saisonCardEvent.getMemo();
        String name = saisonCardEvent.getName();
        LocalDateTime openDate = saisonCardEvent.getOpenDate();
        String socialNetworkingTitle = saisonCardEvent.getSocialNetworkingTitle();
        List<Schedule> schedules = saisonCardEvent.getSchedules();

        assertThat(eventKey, is(notNullValue()));
        assertThat(eventUrl, is("https://www.great-satsumanian.jp"));
        assertThat(jvcUrl, is("http://www.jvcmusic.co.jp/-/Live/A018653/live/233.html"));
        assertThat(memo, is("HP先行予約（先着）受付\r\u003CBR\u003E受付期間：2018/03/27(火)12:00〜2018/04/30(月)23:59\r\u003CBR\u003E\r\u003CBR\u003E※出演日未定"));
        assertThat(name, is("THE GREAT  SATSUMANIAN HESTIVAL2018"));
        assertThat(openDate, is(LocalDateTime.of(2017, 12, 25, 0, 0)));
        assertThat(socialNetworkingTitle, is("【SOIL ＆“PIMP”SESSIONS】｢THE GREAT  SATSUMANIAN HESTIVAL2018｣開催"));
        assertThat(schedules.size(), is(2));

    }

    @Test
    public void shouldPopulateEventScheduleObjectCorrectly() {
        List<Event> currentPostedEvents = webEventDao.getCurrentPostedEvents();

        Event synchronicity18Event = null;

        for (Event event : currentPostedEvents) {

            if (!"SYNCHRONICITY’18".equals(event.getName()))
                continue;

            synchronicity18Event = event;
            break;

        }

        assertNotNull(synchronicity18Event);

        List<Schedule> schedules = synchronicity18Event.getSchedules();

        assertThat(schedules.size(), is(1));

        Schedule schedule = schedules.get(0);

        LocalDate date = schedule.getDate();
        String enterTime = schedule.getEnterTime();
        String link = schedule.getLink();
        String memo = schedule.getMemo();
        String place = schedule.getPlace();
        String prefecture = schedule.getPrefecture();
        String startTime = schedule.getStartTime();

        assertThat(date, is(LocalDate.of(2018, 4, 7)));
        assertThat(enterTime, is("13:00:00"));
        assertThat(link, is(""));
        assertThat(memo, is("開催場所：TSUTAYA O-EAST、TSUTAYA O-WEST、TSUTAYA O-nest、duo MUSIC EXCHANGE、clubasia、VUENOS、Glad、LOFT9"));
        assertThat(place, is("渋谷"));
        assertThat(prefecture, is("東京都"));
        assertThat(startTime, is("14:00:00"));

    }
}
