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
import com.tolstoy.jboto.api.IForeachCommand;
import com.tolstoy.jboto.api.framework.IFrameworkForeachCommand;
import com.tolstoy.jboto.api.framework.IFrameworkCommand;
import com.tolstoy.jboto.api.framework.FrameworkResult;

public class FrameworkForeachCommand extends FrameworkCommand implements IFrameworkForeachCommand {
	private static final Logger logger = LogManager.getLogger( FrameworkForeachCommand.class );

	public FrameworkForeachCommand( String id, String targetClassname, String targetFQClassname, List<IFrameworkCommand> commands ) {
		super( id, targetClassname, targetFQClassname, commands );
	}

	@Override
	public String getShortName() {
		return "foreach " + getTargetClassname();
	}

	@Override
	public FrameworkResult run( IProduct product, IEnvironment env, Object outerExtra ) throws Exception {
		IForeachCommand foreachCommand = (IForeachCommand) getConstructor().newInstance();

		for ( Object innerExtra : foreachCommand.getList( product, env, outerExtra ) ) {
			for ( IFrameworkCommand command : getCommands() ) {
				FrameworkResult res = command.run( product, env, innerExtra );

				if ( res == FrameworkResult.BREAK ) {
					return FrameworkResult.CONTINUE;
				}

				if ( res != FrameworkResult.CONTINUE ) {
					return res;
				}
			}
		}

		return FrameworkResult.CONTINUE;
	}
}
