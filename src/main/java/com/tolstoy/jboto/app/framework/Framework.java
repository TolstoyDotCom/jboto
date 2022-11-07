/*
 * Copyright 2022 Chris Kelly
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.tolstoy.jboto.app.framework;

import java.util.List;

import com.tolstoy.jboto.api.IProduct;
import com.tolstoy.jboto.api.IEnvironment;
import com.tolstoy.jboto.api.framework.IFramework;
import com.tolstoy.jboto.api.framework.IFrameworkCommand;
import com.tolstoy.jboto.api.framework.FrameworkResult;

public class Framework implements IFramework {
	private final String name;
	private final List<IFrameworkCommand> commands;

	Framework( String name, List<IFrameworkCommand> commands ) {
		this.name = name;
		this.commands = commands;
	}

	public FrameworkResult run( IProduct product, IEnvironment env, Object extra ) throws Exception {
		for ( IFrameworkCommand command : getCommands() ) {
			FrameworkResult res = command.run( product, env, extra );
			if ( res != FrameworkResult.CONTINUE ) {
				return res;
			}
		}

		return FrameworkResult.CONTINUE;
	}

	public String getName() {
		return name;
	}

	public List<IFrameworkCommand> getCommands() {
		return commands;
	}

	public String toDebugString( String indent ) {
		String ret = indent + "framework:\n";

		for ( IFrameworkCommand command : getCommands() ) {
			ret += command.toDebugString( indent + "  " ) + "\n";
		}

		return ret;
	}
}
