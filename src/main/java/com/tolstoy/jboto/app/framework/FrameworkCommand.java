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

import com.tolstoy.jboto.api.framework.IFrameworkCommand;

abstract public class FrameworkCommand implements IFrameworkCommand {
	private final String id, targetClassname, packageName;
	private final List<IFrameworkCommand> commands;

	public FrameworkCommand( String id, String targetClassname, String packageName, List<IFrameworkCommand> commands ) {
		this.id = id;
		this.targetClassname = targetClassname;
		this.packageName = packageName;
		this.commands = commands;
	}

	public String getID() {
		return id;
	}

	public String getTargetClassname() {
		return targetClassname;
	}

	public String getTargetPackage() {
		return packageName;
	}

	public String getTargetFQClassname() {
		return packageName + "." + targetClassname;
	}

	public List<IFrameworkCommand> getCommands() {
		return commands;
	}

	protected Constructor<?> getConstructor() throws Exception {
		Class<?> clazz = Class.forName( getTargetFQClassname() );

		return clazz.getConstructor();
	}

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
