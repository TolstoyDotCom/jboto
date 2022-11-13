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

import org.apache.commons.lang3.StringUtils;

import com.tolstoy.jboto.api.framework.IPackageAlias;

class PackageAlias implements IPackageAlias {
	private final String alias, packageName;

	public PackageAlias( String alias, String packageName ) {
		if ( alias == null || alias.length() < 1 || packageName == null || packageName.length() < 1 ) {
			throw new IllegalArgumentException( "bad alias or packageName" );
		}

		this.alias = alias;
		this.packageName = StringUtils.stripEnd( packageName, "." );
	}

	@Override
	public String getAlias() {
		return alias;
	}

	@Override
	public String getPackageName() {
		return packageName;
	}
}
