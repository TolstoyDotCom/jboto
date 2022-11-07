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

import com.tolstoy.jboto.api.framework.IFrameworkBasicCommand;
import com.tolstoy.jboto.api.framework.IFrameworkCommand;
import com.tolstoy.jboto.api.framework.FrameworkResult;
import com.tolstoy.jboto.api.IBasicCommand;
import com.tolstoy.jboto.api.IProduct;
import com.tolstoy.jboto.api.IEnvironment;

public class FrameworkBasicCommand extends FrameworkCommand implements IFrameworkBasicCommand {
	private static final Logger logger = LogManager.getLogger( FrameworkBasicCommand.class );

	public FrameworkBasicCommand( String id, String targetClassname, String packageName, List<IFrameworkCommand> commands ) {
		super( id, targetClassname, packageName, commands );
	}

	public String getShortName() {
		return getTargetClassname();
	}

	public FrameworkResult run( IProduct product, IEnvironment env, Object extra ) throws Exception {
		IBasicCommand basicCommand = (IBasicCommand) getConstructor().newInstance();

		basicCommand.run( product, env, extra );

		return FrameworkResult.CONTINUE;
	}
}
