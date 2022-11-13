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

import com.tolstoy.jboto.api.framework.IPackageAlias;
import com.tolstoy.jboto.api.framework.IFQNResolver;
import com.tolstoy.jboto.api.framework.IFQNResolverFactory;

public class FQNResolverFactory implements IFQNResolverFactory {
	public FQNResolverFactory() {
	}

	@Override
	public IFQNResolver makeResolver( String defaultPackageName ) {
		return new FQNResolver( defaultPackageName );
	}

	@Override
	public IFQNResolver makeResolver( String defaultPackageName, List<IPackageAlias> aliases ) {
		return new FQNResolver( defaultPackageName, aliases );
	}

	@Override
	public IPackageAlias makePackageAlias( String alias, String packageName ) {
		return new PackageAlias( alias, packageName );
	}
}
