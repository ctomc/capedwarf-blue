/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2012, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jboss.capedwarf.common.infinispan;

import java.util.concurrent.Callable;

import org.infinispan.Cache;
import org.jboss.capedwarf.shared.util.Utils;

/**
 * Wrapper Tx callable.
 *
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
public class WrapperTxCallable<K, V, R> extends BaseTxCallable<K, V, R> {
    private Cache<K, V> cache;
    private Callable<R> callable;

    public WrapperTxCallable(Cache<K, V> cache, Callable<R> callable) {
        if (cache == null)
            throw new IllegalArgumentException("Null cache");
        if (callable == null)
            throw new IllegalArgumentException("Null callable");

        this.cache = cache;
        this.callable = callable;
    }

    public R call() {
        try {
            return super.call();
        } catch (Exception e) {
            throw Utils.toRuntimeException(e);
        }
    }

    protected Cache<K, V> getCache() {
        return cache;
    }

    protected R callInTx() throws Exception {
        return callable.call();
    }
}
