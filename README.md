# The Producer-Consumer Problem

## Description

In the producer-consumer problem,
there is a shared resource that a `Producer`
that keeps populating it with data items,
and a `Consumer` that keeps reading data items
and possibly processing them in some way.

Each of the `Producer` and the `Consumer`
are executing their code in their own separate
threads, which means they can both access
the shared resource at the same time, which can
be a problem.

The solution is to simply _lock_ the shared resource
when it is use, blocking any thread trying to access it
at that time, and only unlock it when it is ready
for use again.

## Example

In this example, we have:

- a `Producer` that produces
  prime numbers and puts them in a buffer.
- a `Consumer` that takes those prime numbers
  from the buffer, and writes them to an
  output text file.

The `Producer` takes an integer `N`, and iterates
over all integers from `0` to `N` in search for prime
numbers.

When the `Producer` finds a prime number, it attempts
to put it in the buffer.

If the buffer is empty, the prime number is put safely.

If the buffer is _not_ empty, the `Producer` thread is
**blocked** until it is notified that the buffer is
not full anymore, and is ready to have more
prime numbers put in it.

The `Consumer` on the other hand keeps taking prime
numbers from the buffer as long as the `Producer` is
not done producing (which is handled using a simple
flag in the `Producer`), and writing them to the output
file.

If the `Consumer` tries to take a prime number
from the buffer and did not find any, the `Consumer`
thread will be **blocked** until it is notified that
the buffer is not empty anymore, and is ready to have
prime numbers taken from it.

## Authors

- Amr Ibrahim [@v1AIM](https://github.com/v1AIM)
- Amr Khaled [@Le-Wolfie](https://github.com/Le-Wolfie)
- Sherif Hassan [@sherif3hassan](https://github.com/sherif3hassan)
- Youssef Galal [@youssef-attai](https://github.com/youssef-attai)
