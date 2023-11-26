public class Section5_1 {
    private int criticalValue; // Example: int (replace with required primitive)
    Section5_1() {
    }

    // Example method
    private void method() {
        // Non-critical code
        synchronized(this.getClass()) {
            // Critical code accessing the variable `criticalValue`
        }
        // Non-critical code
    }
}
