/*
 * Sonatype Nexus (TM) Open Source Version
 * Copyright (c) 2008-present Sonatype, Inc.
 * All rights reserved. Includes the third-party code listed at http://links.sonatype.com/products/nexus/oss/attributions.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License Version 1.0,
 * which accompanies this distribution and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Sonatype Nexus (TM) Professional Version is available from Sonatype, Inc. "Sonatype" and "Sonatype Nexus" are trademarks
 * of Sonatype, Inc. Apache Maven is a trademark of the Apache Software Foundation. M2eclipse is a trademark of the
 * Eclipse Foundation. All other trademarks are the property of their respective owners.
 */
package org.sonatype.nexus.common.app;

/**
 * List of available feature flags
 * You can change it's values by editing ${data-dir}/nexus.properties configuration file.
 *
 * @since 3.20
 */
public interface FeatureFlags
{
  /* Go (hosted) repository is experimental. Available values: true, false. Default value: false */
  String FEATURE_GOLANG_HOSTED = "nexus.golang.hosted";

  /* Experimental early access to database externalization. Available values: true, false. Default value: false */
  String EARLY_ACCESS_DATASTORE = "nexus.earlyAccess.datastore.enabled";
  String EARLY_ACCESS_DATASTORE_NAMED = "${nexus.earlyAccess.datastore.enabled:-false}";

  /* Early access developers only. Available values: true, false. Default value: false */
  String EARLY_ACCESS_DATASTORE_DEVELOPER = "nexus.earlyAccess.datastore.developer";
  String EARLY_ACCESS_DATASTORE_DEVELOPER_NAMED = "${nexus.earlyAccess.datastore.developer:-false}";
}
