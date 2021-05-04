/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sprintpay.core.notification.domain.abstraction;

import com.sprintpay.core.notification.domain.Channel;

/**
 *
 * @author champlain
 */
public abstract interface Builder {
    public abstract Object of(Channel channe);
}
