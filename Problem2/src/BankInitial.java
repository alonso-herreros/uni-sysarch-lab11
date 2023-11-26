/**JavaFile******************************************************************
    FileName [Program that simulates a BankAccount]
    Synopsis [Scrooge McDuck and Donald share an account. Scrooge deposits
    money, Donald withdraws money]
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
* Arquitectura de Sistemas II.
* a) Que hace el codigo
* b) Haz que tanto Donald como Scrooge llamen a sus funciones 10 veces
* Que ocurre ahora?
*/

class AccountInitial {
    private float balance = 0;
    private Random rnd = new Random();

    public void depositMoney(float amount) {
        synchronized(this) {
            float tmp = balance;
            System.out.println("(Adding money): the initial balance is: " + tmp);
            try {
                Thread.sleep(rnd.nextInt(1000));
            } catch (Exception e) {}
            tmp += amount;
            balance = tmp;
            System.out.println("(Adding money): the final balance is: " + balance);
        }
    }
    
    public void withdrawMoney(float amount) {
        synchronized(this) {
            float tmp = balance;
            System.out.println("(Withdrawing money): the initial balance is: " + tmp);
            try {
                Thread.sleep(rnd.nextInt(1000));
            } catch (Exception e) {}
            tmp -= amount;
            balance = tmp;
            System.out.println("(Withdrawing money): the final balance is: " + balance);
        }
    }
}

class ScroogeInitial extends Thread {
    private AccountInitial account;

    ScroogeInitial(AccountInitial account) {
        this.account = account;
    }

    public void run() {
        for (int i = 0; i < 10; i++) {
            account.depositMoney(100);
        }
    }
}

class DonaldInitial extends Thread {
    private AccountInitial account;

    DonaldInitial(AccountInitial account) {
        this.account = account;
    }

    public void run() {
        for (int i = 0; i < 10; i++) {
            account.withdrawMoney(100);
        }
    }
}


public class BankInitial {
    public static void main(String[] args) {
        AccountInitial account = new AccountInitial();
        DonaldInitial donald = new DonaldInitial(account);
        ScroogeInitial tiogilito = new ScroogeInitial(account);
        donald.start();
        tiogilito.start();
    }
}