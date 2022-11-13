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
import com.tolstoy.jboto.api.IIfCommand;
import com.tolstoy.jboto.api.framework.IFrameworkIfCommand;
import com.tolstoy.jboto.api.framework.IFrameworkCommand;
import com.tolstoy.jboto.api.framework.FrameworkResult;

public class FrameworkIfCommand extends FrameworkCommand implements IFrameworkIfCommand {
	private static final Logger logger = LogManager.getLogger( FrameworkIfCommand.class );

	public FrameworkIfCommand( String id, String targetClassname, String targetFQClassname, List<IFrameworkCommand> commands ) {
		super( id, targetClassname, targetFQClassname, commands );
	}

	@Override
	public String getShortName() {
		return "if " + getTargetClassname();
	}

	@Override
	public FrameworkResult run( IProduct product, IEnvironment env, Object extra ) throws Exception {
		IIfCommand basicCommand = (IIfCommand) getConstructor().newInstance();

		if ( !basicCommand.test( product, env, extra ) ) {
			return FrameworkResult.CONTINUE;
		}

		for ( IFrameworkCommand command : getCommands() ) {
			FrameworkResult res = command.run( product, env, extra );
			if ( res != FrameworkResult.CONTINUE ) {
				return res;
			}
		}

		return FrameworkResult.CONTINUE;
	}
}
