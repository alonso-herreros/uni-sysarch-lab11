## Section 1.3

> Why do you think the execution between the two executions are diferent?

The first execution is a single thread because it doesn't use the method start,
so it will execute the code in order. The second execution is a multi-thread,
so it will execute the code in parallel. The order of execution is not guaranteed,
but the threads use synchronization and a shared static variable, so they wait
their turn and the order of printed statements is always the same regardles of 
running order.
