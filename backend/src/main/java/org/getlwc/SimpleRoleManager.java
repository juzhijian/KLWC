/*
 * Copyright (c) 2011-2013 Tyler Blair
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ''AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE AUTHOR OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and contributors and should not be interpreted as representing official policies,
 * either expressed or implied, of anybody else.
 */

package org.getlwc;

import org.getlwc.model.Protection;

import java.util.HashMap;
import java.util.Map;

public class SimpleRoleManager implements RoleManager {

    /**
     * The definitions that have been registered
     */
    private Map<Integer, RoleDefinition> definitions = new HashMap<Integer, RoleDefinition>();

    /**
     * Clear all registered roles
     */
    public void clearRoles() {
        definitions.clear();
    }

    /**
     * {@inheritDoc}
     */
    public void registerDefinition(RoleDefinition definition) {
        if (definitions.containsKey(definition.getId())) {
            // TODO our own exception
            throw new UnsupportedOperationException("Role definition already exists for the id " + definition.getId());
        }

        definitions.put(definition.getId(), definition);
    }

    /**
     * {@inheritDoc}
     */
    public RoleDefinition getDefinition(int id) {
        return definitions.get(id);
    }

    /**
     * {@inheritDoc}
     */
    public Role matchAndCreateRoleByName(Protection protection, String name, ProtectionAccess access) {
        for (RoleDefinition definition : definitions.values()) {
            String realName = definition.matchRoleName(name);

            if (realName != null) {
                return definition.createRole(protection, realName, access);
            }
        }

        return null;
    }

}
