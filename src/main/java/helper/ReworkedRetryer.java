package helper;

import io.qameta.atlas.core.internal.Retryer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ReworkedRetryer implements Retryer {

    private final List<Class<? extends Throwable>> exceptionsToNotRetry;
    private Long timeout;
    private Long polling;

    public ReworkedRetryer(final Long timeout, final Long polling, final List<Class<? extends Throwable>> exceptionsToNotRetry) {
        this.exceptionsToNotRetry = new ArrayList<>(exceptionsToNotRetry);
        this.timeout = timeout;
        this.polling = polling;
    }

    public void alsoDoNotRetry(final Class<? extends Throwable> throwable) {
        this.exceptionsToNotRetry.add(throwable);
    }

    @Override
    public void timeoutInSeconds(final int seconds) {
        this.timeout = TimeUnit.SECONDS.toMillis(seconds);
    }

    public void polling(final Long polling) {
        this.polling = polling;
    }

    @Override
    public boolean shouldRetry(final long start, final Throwable e) {
        return shouldRetry(start, timeout, polling, exceptionsToNotRetry, e);
    }

    @Override
    public boolean shouldRetry(final Long start, final Long timeout, final Long polling,
                               final List<Class<? extends Throwable>> exceptionsToNotRetry, final Throwable e) {

        boolean shouldThisExceptionBeRetried = exceptionsToNotRetry.stream().noneMatch(clazz -> clazz.isInstance(e));
        boolean isThereStillTimeToWait = System.currentTimeMillis() < (start + timeout);

        if (shouldThisExceptionBeRetried && isThereStillTimeToWait) {
            try {
                Thread.sleep(polling);
                return true;
            } catch (InterruptedException i) {
                Thread.currentThread().interrupt();
            }
        }
        return false;
    }
}

