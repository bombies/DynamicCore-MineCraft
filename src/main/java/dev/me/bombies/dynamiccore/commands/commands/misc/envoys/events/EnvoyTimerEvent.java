package dev.me.bombies.dynamiccore.commands.commands.misc.envoys.events;

import dev.me.bombies.dynamiccore.DynamicCore;
import dev.me.bombies.dynamiccore.utils.config.envoy.EnvoyConfig;

import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class EnvoyTimerEvent {

    public EnvoyTimerEvent() {
        EnvoyConfig config = new EnvoyConfig();
        List<Integer> times = config.getTimes();

        if (times.isEmpty()) {
            DynamicCore.logger.warning("[WARN] There are no configured times for envoys!");
            return;
        }

        List<Date> dates = new ArrayList<>();

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("EST"));

        for (int time : times)
            dates.add(new Date(
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH),
                    time, 0, 0
            ));

        ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
        for (Date date : dates) {
            long millis = date.getTime() - System.currentTimeMillis();
            service.schedule(new EnvoySpawnEvent(), millis, TimeUnit.MILLISECONDS);
        }
    }
}
