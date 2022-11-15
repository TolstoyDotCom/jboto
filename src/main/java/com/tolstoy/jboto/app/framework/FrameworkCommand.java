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
import java.lang.reflect.Constructor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.tolstoy.basic.api.installation.DebugLevel;
import com.tolstoy.jboto.api.framework.IFrameworkCommand;
import com.tolstoy.jboto.api.IProduct;
import com.tolstoy.jboto.api.IEnvironment;

abstract public class FrameworkCommand implements IFrameworkCommand {
	private static final Logger logger = LogManager.getLogger( FrameworkCommand.class );

	private final String id, targetClassname, targetFQClassname;
	private final List<IFrameworkCommand> commands;

	public FrameworkCommand( String id, String targetClassname, String targetFQClassname, List<IFrameworkCommand> commands ) {
		this.id = id;
		this.targetClassname = targetClassname;
		this.targetFQClassname = targetFQClassname;
		this.commands = commands;
	}

	@Override
	public String getID() {
		return id;
	}

	@Override
	public String getTargetClassname() {
		return targetClassname;
	}

	@Override
	public String getTargetFQClassname() {
		return targetFQClassname;
	}

	@Override
	public List<IFrameworkCommand> getCommands() {
		return commands;
	}

	protected Constructor<?> getConstructor() throws Exception {
		Class<?> clazz = Class.forName( getTargetFQClassname() );

		return clazz.getConstructor();
	}

	protected void beforeRun( IProduct product, IEnvironment env, Object extra, int index ) {
		DebugLevel debugLevel = env.getDebugLevel();
		if ( debugLevel == null || debugLevel == DebugLevel.NONE ) {
			return;
		}

		logger.info( "  ** RUNNING " + getShortName() + " **" );
	}

	protected void afterRun( IProduct product, IEnvironment env, Object extra, int index ) {
		DebugLevel debugLevel = env.getDebugLevel();
		if ( debugLevel == null || debugLevel == DebugLevel.NONE ) {
			return;
		}

		logger.info( "  ** RAN " + getShortName() + " **" );
	}

	@Override
	public String toDebugString( String indent ) {
		String ret = indent + getShortName();

		if ( getCommands() == null || getCommands().size() < 1 ) {
			return ret;
		}

		ret += "\n";
		for ( IFrameworkCommand command : getCommands() ) {
			ret += command.toDebugString( indent + "  " ) + "\n";
		}

		return ret;
	}
}
