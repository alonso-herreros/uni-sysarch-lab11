
/**JavaFile******************************************************************
    FileName [Program that prints the dialogue between Luke Skywalker and
    Darth Vader]
    Synopsis [Contains three classes, that should be executed as threads ]
    Author [Iria Estevez-Ayres <ayres@it.uc3m.es>]
    Copyright [Copyright (c) 2019 Carlos III University of Madrid
    All rights reserved.
    Permission is hereby granted, without written agreement and without license
    or royalty fees, to use, copy, modify, and distribute this software and its
    documentation for any purpose, provided that the above copyright notice and
    the following two paragraphs appear in all copies of this software.
    IN NO EVENT SHALL THE CARLOS III UNIVERSITY OF MADRID BE LIABLE TO ANY PARTY
    FOR DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES ARISING
    OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF THE CARLOS III
    UNIVERSITY OF MADRID HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
    THE CARLOS III UNIVERSITY OF MADRID SPECIFICALLY DISCLAIMS ANY WARRANTIES,
    INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
    FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE PROVIDED HEREUNDER IS ON AN
    "AS IS" BASIS, AND CARLOS III UNIVERSITY OF MADRID HAS NO OBLIGATION TO
    PROVIDE MAINTENANCE, SUPPORT, UPDATES, ENHANCEMENTS, OR MODIFICATIONS.]
******************************************************************************/
import java.util.*;

/*
* Arquitectura de Sistemas (2021-22)
*
* Ejercicio en clase:
*
* Synchronize Luke and Darth Vader so that the dialogue makes sense.
*
* The code to synchronize must be inside class Lock
*
*/

class Lock {
    private int value = 0;

    public enum ID {
        DV(0), LS(1);
        private final int v;

        ID(int v) { this.v = v; }
        public int getValue() { return v; }
    }

    public synchronized void await(ID v_id) {
        await(v_id.getValue());
    }
    public synchronized void await(int v) {
        v = 1 << v;
        while ((value & v) == 0) {
            try { wait(); } catch (InterruptedException e) { }
        }
        value -= v;
    }

    public synchronized void announce(ID v_id) {
        announce(v_id.getValue());
    }
    public synchronized void announce(int v) {
        value = value | (1<<v);
        notifyAll();
    }
}


class Luke extends Thread {
    static final Lock.ID lockID = Lock.ID.LS;
    private Lock lock;

    Luke(Lock lock) {
        this.lock = lock;
    }

    public void run() {
        Random rnd = new Random();

        lock.await(DarthVader.lockID);
        System.out.println("(Luke): He told me enough! He told me YOU killed him!");
        lock.announce(lockID);

        try {
            Thread.sleep(rnd.nextInt(2000));
        } catch (Exception e) {}

        lock.await(DarthVader.lockID);
        System.out.println("(Luke): No. No. That's not true. That's impossible!");
        lock.announce(lockID);

        try {
            Thread.sleep(rnd.nextInt(2000));
        } catch (Exception e) {}

        lock.await(DarthVader.lockID);
        System.out.println("(Luke): No! No!");
        lock.announce(lockID);
    }
}


class DarthVader extends Thread {
    static final Lock.ID lockID = Lock.ID.DV;
    private Lock lock;

    DarthVader(Lock lock) {
        this.lock = lock;
    }

    public void run() {
        Random rnd = new Random();
        System.out.println("(DV): If you only knew the power of the Dark Side. Obi-Wan never told you what happened to your father.");
        lock.announce(lockID);

        try {
            Thread.sleep(rnd.nextInt(2000));
        } catch (Exception e) {}

        lock.await(Luke.lockID);
        System.out.println("(DV): No. I am your father.");
        lock.announce(lockID);

        try {
            Thread.sleep(rnd.nextInt(2000));
        } catch (Exception e) {}

        lock.await(Luke.lockID);
        System.out.println("(DV): Search your feelings, you KNOW it to be true!");
        lock.announce(lockID);
    }
}

public class StarWarsAlt {
    public static void main(String[] args) {
        Lock lock = new Lock();
        Luke luke = new Luke(lock);
        DarthVader daddy = new DarthVader(lock);
        daddy.start();
        luke.start();
    }
}