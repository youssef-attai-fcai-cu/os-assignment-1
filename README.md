# The Producer-Consumer Problem

## Description

In the Producer-Consumer problem,
there is a shared resource that a `Producer`
keeps populating with data items,
and a `Consumer` that keeps reading data items
from it, and possibly processing them in some way.

Each of the `Producer` and the `Consumer`
are executing their code in their own separate
threads, which means they can both access
the shared resource at the same time, which can
be a problem.

The solution is to simply _lock_ the shared resource
when it is in use, blocking any thread trying to access it
at that time, and only unlock it when it is ready
for use again.

## Example

In this example, we have:

- a `Producer` that produces
  prime numbers and puts them in a Buffer.
- a `Consumer` that takes those prime numbers
  from the Buffer, and writes them to an
  output text file.

So, the Buffer is the shared resource.

The `Producer` takes an integer `N`, and iterates
over all integers from `0` to `N` in search for prime
numbers.

When the `Producer` finds a prime number, it attempts
to put it in the Buffer.

If the Buffer is empty, the prime number is put safely.

If the Buffer is _not_ empty, the `Producer` thread is
**blocked** until it is notified that the Buffer is
not full anymore, and is ready to have more
prime numbers put in it.

The `Consumer` on the other hand keeps taking prime
numbers from the Buffer as long as the `Producer` is
not done producing (which is handled using a simple
flag in the `Producer`), and writing them to the output
file.

If the `Consumer` tries to take a prime number
from the Buffer and did not find any, the `Consumer`
thread will be **blocked** until it is notified that
the Buffer is not empty anymore, and is ready to have
prime numbers taken from it.

## Authors

- Amr Ibrahim [@v1AIM](https://github.com/v1AIM)
- Amr Khaled [@Le-Wolfie](https://github.com/Le-Wolfie)
- Sherif Hassan [@sherif3hassan](https://github.com/sherif3hassan)
- Youssef Galal [@youssef-attai](https://github.com/youssef-attai)


## Acknowledgements

- Diaa Essam [@DiaaEssam](https://github.com/DiaaEssam)
- [Defog Tech - Java Concurrency Interview: Implement Producer Consumer pattern using wait-notify](https://www.youtube.com/watch?v=UOr9kMCCa5g)
- [W3Schools - Java Create and Write To Files](https://www.w3schools.com/java/java_files_create.asp)
- [Javatpoint - Prime Number Program in Java](https://www.javatpoint.com/prime-number-program-in-java)
- [GeeksforGeeks - Reentrant Lock in Java](https://www.geeksforgeeks.org/reentrant-lock-java/)
