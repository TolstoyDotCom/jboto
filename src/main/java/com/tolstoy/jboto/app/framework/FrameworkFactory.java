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

import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import com.github.cliftonlabs.json_simple.Jsoner;
import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.tolstoy.jboto.api.framework.IFrameworkFactory;
import com.tolstoy.jboto.api.framework.IFramework;
import com.tolstoy.jboto.api.framework.IFrameworkCommand;

public class FrameworkFactory implements IFrameworkFactory {
	private static final Logger logger = LogManager.getLogger( FrameworkFactory.class );

	private final String packageName;

	public FrameworkFactory( String packageName ) {
		this.packageName = packageName;
	}

	public IFramework makeFrameworkFromJSON( String name, String json ) {
		JsonArray ary = Jsoner.deserialize( json, new JsonArray() );
		if ( ary.size() != 1 ) {
			throw new IllegalArgumentException( "must have just one root object" );
		}

		Map<String,Object> rootMap = ary.getMap( 0 );
		List<Object> list = (List<Object>) rootMap.get( "commands" );
		String id = (String) rootMap.get( "id" );

		List<IFrameworkCommand> commands = createCommands( list );

		//logger.info( commands );

		return new Framework( id, commands );
	}

	protected List<IFrameworkCommand> createCommands( List<Object> list ) {
		List<IFrameworkCommand> ret = new ArrayList<IFrameworkCommand>();

		for ( Object obj : list ) {
			Map<String,Object> map = (Map<String,Object>) obj;

			String id = (String) map.get( "id" );
			String type = (String) map.get( "type" );
			String classname = (String) map.get( "classname" );

			List<Object> innerList = (List<Object>) map.get( "commands" );
			List<IFrameworkCommand> innerCommands = innerList != null ? createCommands( innerList ) : new ArrayList<IFrameworkCommand>( 1 );

			IFrameworkCommand command;
			if ( "command".equals( type ) ) {
				ret.add( new FrameworkBasicCommand( id, classname, packageName, innerCommands ) );
			}
			else if ( "if".equals( type ) ) {
				ret.add( new FrameworkIfCommand( id, classname, packageName, innerCommands ) );
			}
			else if ( "foreach".equals( type ) ) {
				ret.add( new FrameworkForeachCommand( id, classname, packageName, innerCommands ) );
			}
			else if ( "break".equals( type ) ) {
				ret.add( new FrameworkBreakCommand( id, classname, packageName, innerCommands ) );
			}
			else {
				throw new IllegalArgumentException( "unknown type " + type );
			}
		}

		return ret;
	}
}
