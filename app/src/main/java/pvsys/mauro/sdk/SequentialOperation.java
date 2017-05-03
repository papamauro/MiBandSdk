package pvsys.mauro.sdk;

public class SequentialOperation {

    private final static Logger LOG = new Logger(BTSequentialClient.class.getSimpleName());

    public static final long SLEEP_TIME = 1;
    public static final long DEFAULT_TIMEOUT = 1;
    public static final String UNKNOWN_OP = "unknown";

    private String opName = UNKNOWN_OP;
    private long startTime = -1;
    private long timeout = -1;
    private boolean completed = false;
    private Object result = null;
    private RuntimeException error = null;

    public synchronized void waitForOperation(final String opName) {
        waitForOperation(opName, DEFAULT_TIMEOUT);
    }

    public synchronized void waitForOperation() {
        waitForOperation(UNKNOWN_OP, DEFAULT_TIMEOUT);
    }

    public synchronized void waitForOperation(final long timeout) {
        waitForOperation(UNKNOWN_OP, timeout);
    }

    public Object waitForOperation(final String opName, final long timeout) {
        if(this.startTime != -1) {
            throw new RuntimeException("unexpected operation overlap, starting sequential operation " + opName + " while operation " + this.opName + " still in execution");
        }
        this.startTime = System.currentTimeMillis();
        this.timeout = timeout;
        this.completed = false;
        this.result = null;
        this.error = null;
        this.opName = opName;
        LOG.debug("waiting for operation " + this.opName);
        while(true) {
            synchronized (this) {
                if(completed) {
                    if(error != null) {
                        throw error;
                    } else {
                        return result;
                    }
                }
                if(System.currentTimeMillis() > startTime + timeout) {
                    LOG.debug("operation "+ opName + " timed out after " + timeout + " ms");
                    throw new TimeoutOperationException("operation "+ opName + " timed out after " + timeout + " ms");
                }
            }
            try {Thread.sleep(SLEEP_TIME);} catch (InterruptedException e) {}
        }
    }

    public synchronized void notifyCompleted() {
        notifyCompleted(null);
    }

    public synchronized void notifyCompleted(final Object result) {
        this.completed = true;
        this.result = result;
        this.error = null;
        this.startTime = -1;
        LOG.debug("completed sequential operation " + this.opName);
    }

    public synchronized void notifyError(final RuntimeException error) {
        this.completed = true;
        this.result = null;
        this.error = error;
        this.startTime = -1;
        LOG.debug("failed sequential operation " + this.opName + " with exception: " + error.getMessage());
    }

    public static class TimeoutOperationException  extends RuntimeException{
        public TimeoutOperationException(String message) {
            super(message);
        }
    }
}
