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
package org.sonatype.nexus.coreui.internal.blobstore;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.blobstore.BlobStoreDescriptor;
import org.sonatype.nexus.blobstore.api.BlobStoreManager;
import org.sonatype.nexus.blobstore.quota.BlobStoreQuota;
import org.sonatype.nexus.repository.manager.RepositoryManager;
import org.sonatype.nexus.rest.Resource;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Streams.stream;
import static java.util.stream.Collectors.toList;
import static org.sonatype.nexus.coreui.internal.blobstore.BlobStoreUIResource.RESOURCE_PATH;

/**
 * @since 3.30
 */
@Named
@Singleton
@Path(RESOURCE_PATH)
public class BlobStoreUIResource
    extends ComponentSupport
    implements Resource
{
  static final String RESOURCE_PATH = "/internal/ui/blobstores";

  private final BlobStoreManager blobStoreManager;

  private final List<BlobStoreTypesUIResponse> blobStoreTypes;

  private final List<BlobStoreQuotaTypesUIResponse> blobStoreQuotaTypes;

  private final RepositoryManager repositoryManager;

  @Inject
  public BlobStoreUIResource(final BlobStoreManager blobStoreManager,
                             final Map<String, BlobStoreDescriptor> blobStoreDescriptors,
                             final Map<String, BlobStoreQuota> quotaFactories,
                             final RepositoryManager repositoryManager)
  {
    this.blobStoreManager = checkNotNull(blobStoreManager);
    this.blobStoreTypes = blobStoreDescriptors.entrySet().stream()
        .map(BlobStoreTypesUIResponse::new).collect(toList());
    this.blobStoreQuotaTypes = quotaFactories.entrySet().stream()
        .map(BlobStoreQuotaTypesUIResponse::new).collect(toList());
    this.repositoryManager = checkNotNull(repositoryManager);
  }

  @RequiresAuthentication
  @RequiresPermissions("nexus:blobstores:read")
  @GET
  public List<BlobStoreUIResponse> listBlobStores() {
    return stream(blobStoreManager.browse()).map(BlobStoreUIResponse::new).collect(toList());
  }

  @RequiresAuthentication
  @RequiresPermissions("nexus:blobstores:read")
  @GET
  @Path("/types")
  public List<BlobStoreTypesUIResponse> listBlobStoreTypes() {
    return blobStoreTypes;
  }

  @RequiresAuthentication
  @RequiresPermissions("nexus:blobstores:read")
  @GET
  @Path("/usage/{name}")
  public BlobStoreUsageUIResponse getBlobStoreUsage(@PathParam("name") final String name) {
    long repositoryUsage = repositoryManager.blobstoreUsageCount(name);
    long blobStoreUsage = blobStoreManager.blobStoreUsageCount(name);

    return new BlobStoreUsageUIResponse(repositoryUsage, blobStoreUsage);
  }

  @RequiresAuthentication
  @RequiresPermissions("nexus:blobstores:read")
  @GET
  @Path("/quotaTypes")
  public List<BlobStoreQuotaTypesUIResponse> listQuotaTypes() {
    return blobStoreQuotaTypes;
  }
}


