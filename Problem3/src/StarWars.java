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
    public synchronized void espero(){
        while (value == 0) {
            try { wait(); } catch (InterruptedException e) {}
        }
        value = 0;
    }
    public synchronized void aviso(){
            value++;
            notifyAll();
    }
}

class Luke extends Thread{
    private Lock lockLS, lockDV;
    Luke(Lock lockLS, Lock lockDV){
        this.lockLS = lockLS;
        this.lockDV = lockDV;
    }
    public void run(){
        Random rnd = new Random();
        lockDV.espero();
        System.out.println("(Luke): He told me enough! He told me YOU killed him!");
        lockLS.aviso();
        try {
            Thread.sleep(rnd.nextInt(2000));
        } catch (Exception e) {}
        lockDV.espero();
        System.out.println("(Luke): No. No. That's not true. That's impossible!");
        lockLS.aviso();
        try {
            Thread.sleep(rnd.nextInt(2000));
        } catch (Exception e) {}
        lockDV.espero();
        System.out.println("(Luke): No! No!");
        lockLS.aviso();
    }
}

class DarthVader extends Thread{
    private Lock lockLS, lockDV;
    DarthVader(Lock lockLS, Lock lockDV){
        this.lockLS = lockLS;
        this.lockDV = lockDV;
    }
    public void run(){
        Random rnd = new Random();
        System.out.println("(DV): If you only knew the power of the Dark Side. Obi-Wan never told you what happened to your father.");
        lockDV.aviso();
        try {
            Thread.sleep(rnd.nextInt(2000));
        } catch (Exception e) {}
        lockLS.espero();
        System.out.println("(DV): No. I am your father.");
        lockDV.aviso();
        try {
            Thread.sleep(rnd.nextInt(2000));
        } catch (Exception e) {}
        lockLS.espero();
        System.out.println("(DV): Search your feelings, you KNOW it to be true!");
        lockDV.aviso();
    }
}

public class StarWars{
    public static void main(String[] args){
        Lock lockLS = new Lock();
        Lock lockDV = new Lock();
        Luke luke = new Luke(lockLS, lockDV);
        DarthVader daddy = new DarthVader(lockLS, lockDV);
        daddy.start();
        luke.start();
    }
}