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

package org.jboss.capedwarf.bytecode.blacklist;

import javassist.bytecode.Bytecode;
import org.jboss.capedwarf.shared.blacklist.BlackList;

/**
 * Object access.
 *
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
class ObjectAccessLineRewriter implements LineRewriter {
    public int visit(LineContext context) throws Exception {
        String className = context.getClassName();
        if (BlackList.getBlackList().contains(className)) {
            // reject
            Bytecode bytecode = new Bytecode(context.getConstPool());
            bytecode.addLdc(className);
            bytecode.addInvokestatic(Restrictions.class.getName(), "reject", "(Ljava/lang/String;)V");
            // insert before invocation
            context.insertAtIndex(bytecode);
            return 1;
        }
        return 0;
    }
}
