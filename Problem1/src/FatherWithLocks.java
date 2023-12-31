/**JavaFile******************************************************************
    FileName [Example with Locks]
    Synopsis [Example of Locks]
    Author [Iria Estevez-Ayres <ayres@it.uc3m.es>]
    Copyright [Copyright (c) 2020 Carlos III University of Madrid
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
/**
* Main class of the Java program.
*/
class SonWithLocks extends Thread {
    private int ident;
    static int contador;

    public SonWithLocks (int id) {
        this.ident = id;
    }

    public void run() {
        if (this.ident == 0) {
            System.out.println("Hola");
            synchronized(SonWithLocks.class) {
                contador++;
                SonWithLocks.class.notifyAll();
            }
        } else if (this.ident == 1) {
            synchronized(SonWithLocks.class) {
                while (contador != 1) {
                    try {
                        SonWithLocks.class.wait();
                    } catch(Exception e) {}
                }
            }
            System.out.println("Luke");
            synchronized(SonWithLocks.class) {
                contador++;
                SonWithLocks.class.notifyAll();
            }
        } else {
            synchronized(SonWithLocks.class) {
                while (contador != 2) {
                    try {
                        SonWithLocks.class.wait();
                    } catch(Exception e) {}
                }
            }
            System.out.println("Skywalker");
            synchronized(SonWithLocks.class) {
                contador--;
            }
        }
    }
}

public class FatherWithLocks {
    public static void main(String[] arg) {
        SonWithLocks h1, h2, h3;
        h1 = new SonWithLocks(0);
        h2 = new SonWithLocks(1);
        h3 = new SonWithLocks(2);
        h2.start();
        h1.start();
        h3.start();
        try {
            h1.join();
            h2.join();
            h3.join();
        } catch(Exception e) {}
        System.out.println("Soy tu padre");
    }
}