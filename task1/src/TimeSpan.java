public class TimeSpan {

    private int hours;
    private int minutes;

    public TimeSpan() {
        this.hours = 0;
        this.minutes = 0;
    }

    public TimeSpan(int minutes) {
        if (minutes < 0) {
            throw new IllegalArgumentException("Minutes must be non-negative");
        }
        this.hours = minutes / 60;
        this.minutes = minutes % 60;
    }

    public TimeSpan(int hours, int minutes) {
        if (hours < 0 || minutes < 0) {
            throw new IllegalArgumentException("Hours and minutes must be non-negative");
        }
        this.hours = hours + minutes / 60;
        this.minutes = minutes % 60;
    }

    public TimeSpan(TimeSpan other) {
        if (other == null) {
            throw new NullPointerException("Other TimeSpan must not be null");
        }
        this.hours = other.getHours();
        this.minutes = other.getMinutes();
    }

    public int getHours() {
        return this.hours;
    }

    public int getMinutes() {
        return this.minutes;
    }

    public int getTotalMinutes() {
        return this.hours * 60 + this.minutes;
    }

    public double getTotalHours() {
        return this.hours + (double) this.minutes / 60.0;
    }

    public void add(int addHours, int addMinutes) {
        if (addHours < 0 || addMinutes < 0) {
            throw new IllegalArgumentException("Hours and minutes to add must be non-negative");
        }
        int total = this.getTotalMinutes() + addHours * 60 + addMinutes;
        this.hours = total / 60;
        this.minutes = total % 60;
    }

    public void add(int addMinutes) {
        if (addMinutes < 0) {
            throw new IllegalArgumentException("Minutes to add must be non-negative");
        }
        int total = this.getTotalMinutes() + addMinutes;
        this.hours = total / 60;
        this.minutes = total % 60;
    }

    public void add(TimeSpan other) {
        if (other == null) {
            throw new NullPointerException("Other TimeSpan must not be null");
        }
        add(other.getHours(), other.getMinutes());
    }

    public void subtract(int subHours, int subMinutes) {
        if (subHours < 0 || subMinutes < 0) {
            throw new IllegalArgumentException("Hours and minutes to subtract must be non-negative");
        }
        int total = this.getTotalMinutes() - (subHours * 60 + subMinutes);
        if (total <= 0) {
            this.hours = 0;
            this.minutes = 0;
        } else {
            this.hours = total / 60;
            this.minutes = total % 60;
        }
    }

    public void subtract(int subMinutes) {
        if (subMinutes < 0) {
            throw new IllegalArgumentException("Minutes to subtract must be non-negative");
        }
        int total = this.getTotalMinutes() - subMinutes;
        if (total <= 0) {
            this.hours = 0;
            this.minutes = 0;
        } else {
            this.hours = total / 60;
            this.minutes = total % 60;
        }
    }

    public void subtract(TimeSpan other) {
        if (other == null) {
            throw new NullPointerException("Other TimeSpan must not be null");
        }
        subtract(other.getHours(), other.getMinutes());
    }

    public void scale(int factor) {
        if (factor <= 0) {
            throw new IllegalArgumentException("Scale factor must be positive");
        }
        int total = this.getTotalMinutes() * factor;
        this.hours = total / 60;
        this.minutes = total % 60;
    }

    @Override
    public String toString() {
        return String.format("%d:%02d", this.hours, this.minutes);
    }
}