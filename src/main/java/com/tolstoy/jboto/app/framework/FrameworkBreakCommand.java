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
import com.tolstoy.jboto.api.framework.IFrameworkBreakCommand;
import com.tolstoy.jboto.api.framework.IFrameworkCommand;
import com.tolstoy.jboto.api.framework.FrameworkResult;

public class FrameworkBreakCommand extends FrameworkCommand implements IFrameworkBreakCommand {
	private static final Logger logger = LogManager.getLogger( FrameworkBreakCommand.class );

	public FrameworkBreakCommand( String id, String targetClassname, String packageName, List<IFrameworkCommand> commands ) {
		super( id, targetClassname, packageName, commands );
	}

	public String getShortName() {
		return "break";
	}

	public FrameworkResult run( IProduct product, IEnvironment env, Object extra ) throws Exception {
		return FrameworkResult.BREAK;
	}
}
