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
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.tolstoy.jboto.api.framework.IFQNResolver;
import com.tolstoy.jboto.api.framework.IPackageAlias;

class FQNResolver implements IFQNResolver {
	private static final Logger logger = LogManager.getLogger( FQNResolver.class );

	private static final String PACKAGE_SEPARATOR = ".";
	private static final String ALIAS_SEPARATOR = ":";

	private final List<IPackageAlias> aliases;
	private final String defaultPackageName;

	public FQNResolver( String defaultPackageName ) {
		this.defaultPackageName = StringUtils.stripEnd( defaultPackageName, PACKAGE_SEPARATOR );
		this.aliases = new ArrayList<IPackageAlias>();
	}

	public FQNResolver( String defaultPackageName, List<IPackageAlias> aliases ) {
		this.defaultPackageName = defaultPackageName;
		this.aliases = aliases;
	}

	@Override
	public String resolve( String shortName ) throws Exception {
		if ( shortName == null || shortName.length() < 1 ) {
			throw new IllegalArgumentException( "bad shortName" );
		}

		if ( shortName.contains( PACKAGE_SEPARATOR ) ) {
			return shortName;
		}

		if ( !shortName.contains( ALIAS_SEPARATOR ) ) {
			return defaultPackageName + PACKAGE_SEPARATOR + shortName;
		}

		String[] components = StringUtils.split( shortName, ALIAS_SEPARATOR );
		if ( components == null ||
				components.length != 2 ||
				components[ 0 ] == null ||
				components[ 0 ].length() < 1 ||
				components[ 1 ] == null ||
				components[ 1 ].length() < 1 ) {
			throw new IllegalArgumentException( "Bad format. Should be 'alias:classname', instead was " + shortName );
		}

		for ( IPackageAlias alias : aliases ) {
			if ( components[ 0 ].equals( alias.getAlias() ) ) {
				return alias.getPackageName() + PACKAGE_SEPARATOR + components[ 1 ];
			}
		}

		throw new IllegalArgumentException( "shortName does not match any aliases: " + shortName );
	}
}
