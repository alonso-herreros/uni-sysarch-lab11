> ## PROBLEM 4 – Extraordinary exam 16.06.2023
> Given the following software solution for the critical section problem, indicate which of the three fundamental
> requirements it satisfies and provide a reasoned justification for each answer. Assume that "turn" is a shared
> variable that is initialized at the beginning, and the code is executed by two processes, P0 and P1, as follows:
>
> Process P0 code
>
> ```java
> do {
>     while (turn == 1);
>     critical section
>     turn = 1;
>     reminder section
> } while (true);
> ```
>
> Process P1 code
>
> ```java
> do {
>     while (turn == 0);
>     critical section
>     turn = 0;
>     reminder section
> } while (true);
> ```

This solution satisfies the **mutual exclusion** requirement, since the critical section is
protected by a while loop that checks the value of the shared variable `turn` before enetering
the critical section. Since each process will only enter the critical section if the other process
has set `turn` to its own value, only one process can be in the critical section at a time.

This solution does not satisfy the **progress** requirement, since it is possible for a process
to set the turn to the other process's value, while the other process doesn't need it. If the other
process then doesn't access the critical section, the first process will be stuck waiting for the
other process to yield the turn, even if the critical section is not being used.

This solution satisfies the **bounded waiting** requirement, since the process will wait until a
condition is met, and not indefinitely. The process will wait until the `turn` variable is set to
its required value, and then it will enter the critical section.
