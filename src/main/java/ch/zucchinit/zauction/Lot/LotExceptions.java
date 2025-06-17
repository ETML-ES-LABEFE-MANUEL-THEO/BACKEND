package ch.zucchinit.zauction.Lot;

public class LotExceptions {
    public static class NotPublishedException extends RuntimeException {
        public NotPublishedException() { super ("Le lot n'est pas publié"); }
    }

    public static class AlreadyPublishedException extends RuntimeException {
        public AlreadyPublishedException() { super("Le lot est déjà publié");}
    }

    public static class NotClosedException extends RuntimeException {
        public NotClosedException() { super ("Le lot n'est pas clôturé"); }
    }

    public static class AlreadyClosedException extends RuntimeException {
        public AlreadyClosedException() { super("Lot lot est déjà clôturé"); }
    }

    public static class AlreadyTransferredException extends RuntimeException {
        public AlreadyTransferredException() { super("Le lot est déjà transféré"); }
    }
}
