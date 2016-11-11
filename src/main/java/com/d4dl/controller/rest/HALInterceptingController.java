package com.d4dl.controller.rest;

import com.d4dl.model.Bid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.mapping.RepositoryResourceMappings;
import org.springframework.data.rest.core.mapping.ResourceMetadata;
import org.springframework.data.rest.core.mapping.SearchResourceMappings;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.ProfileController;
import org.springframework.data.rest.webmvc.ProfileResourceProcessor;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.core.EmbeddedWrappers;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by joshuadeford on 11/10/16.
 */
public abstract class HALInterceptingController {
    private static final EmbeddedWrappers WRAPPERS = new EmbeddedWrappers(false);

    @Autowired
    private RepositoryRestConfiguration config;

    @Autowired
    RepositoryResourceMappings resourceMappings;

    @Autowired
    private RepositoryEntityLinks entityLinks;

    @Autowired
    PagedResourcesAssembler<Object> pagedResourcesAssembler;

    public Resources<?> createResourceResponse(Class<?> domainType, Pageable pageable, PersistentEntityResourceAssembler assembler, Page<Bid> page) throws HttpRequestMethodNotSupportedException {
        Link baseLink = entityLinks.linkToPagedResource(domainType, pageable);

        Resources<?> result = toResources(page, assembler, domainType, baseLink);
        result.add(getCollectionResourceLinks(domainType));
        return result;
    }


    //All this code was yanked out of RepositoryEntityController and AbstractRepositoryRestController.

    protected Resources<?> toResources(Page<?> page, PersistentEntityResourceAssembler assembler, Class<?> domainType, Link baseLink) {
        return entitiesToResources(page, assembler, domainType, baseLink);
    }

    protected Resources<?> entitiesToResources(Page<?> page, PersistentEntityResourceAssembler assembler, Class<?> domainType, Link baseLink) {
        if (page.getContent().isEmpty()) {
            return pagedResourcesAssembler.toEmptyResource(page, domainType, baseLink);
        }

        return baseLink == null ? pagedResourcesAssembler.toResource((Page<Object>)page, assembler) : pagedResourcesAssembler.toResource((Page<Object>)page, assembler, baseLink);
    }

    private List<Link> getCollectionResourceLinks(Class domainType) {
        ResourceMetadata metadata = resourceMappings.getMetadataFor(domainType);
        SearchResourceMappings searchMappings = resourceMappings.getSearchResourceMappings(domainType);

        List<Link> links = new ArrayList();
        links.add(new Link(ProfileController.getPath(this.config, metadata), ProfileResourceProcessor.PROFILE_REL));

        if (searchMappings.isExported()) {
            links.add(entityLinks.linkFor(metadata.getDomainType()).slash(searchMappings.getPath())
                    .withRel(searchMappings.getRel()));
        }

        return links;
    }

    protected Resources<?> entitiesToResources(Iterable<Object> entities, PersistentEntityResourceAssembler assembler, Class<?> domainType) {
        if (!entities.iterator().hasNext()) {
            List<Object> content = Arrays.asList(WRAPPERS.emptyCollectionOf(domainType));
            return new Resources(content, getDefaultSelfLink());
        }

        List<Resource<Object>> resources = new ArrayList<Resource<Object>>();
        for (Object obj : entities) {
            resources.add(obj == null ? null : assembler.toResource(obj));
        }
        return new Resources<Resource<Object>>(resources, getDefaultSelfLink());
    }

    protected Link getDefaultSelfLink() {
        return new Link(ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString());
    }
}
