package org.queue.application.queue_application.data;

import org.queue.application.queue_application.models.Url;
import org.springframework.data.repository.CrudRepository;

public interface UrlRepository extends CrudRepository<Url,Integer> {

    Url getByShortUrl(String originalString);
}