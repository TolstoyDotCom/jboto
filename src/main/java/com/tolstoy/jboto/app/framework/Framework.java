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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.tolstoy.jboto.api.IProduct;
import com.tolstoy.jboto.api.IEnvironment;
import com.tolstoy.jboto.api.framework.IFramework;
import com.tolstoy.jboto.api.framework.IFrameworkCommand;
import com.tolstoy.jboto.api.framework.FrameworkResult;
import com.tolstoy.jboto.api.IBasicCommand;

public class Framework implements IFramework {
	private static final Logger logger = LogManager.getLogger( Framework.class );

	private final String name, finallyFQN;
	private final List<IFrameworkCommand> commands;

	Framework( String name, List<IFrameworkCommand> commands, String finallyFQN ) {
		this.name = name;
		this.commands = commands;
		this.finallyFQN = finallyFQN;
	}

	public FrameworkResult run( IProduct product, IEnvironment env, Object extra, int index ) throws Exception {
		try {
			for ( IFrameworkCommand command : getCommands() ) {
				FrameworkResult res = command.run( product, env, extra, index );
				if ( res != FrameworkResult.CONTINUE ) {
					return res;
				}
			}

			return FrameworkResult.CONTINUE;
		}
		finally {
			if ( finallyFQN != null ) {
				try {
					IBasicCommand finallyCommand = (IBasicCommand) Class.forName( finallyFQN ).getConstructor().newInstance();
					finallyCommand.run( product, env, extra, index );
				}
				catch ( Exception e ) {
					logger.error( "exception running finally command " + finallyFQN, e );
				}
			}
		}
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
