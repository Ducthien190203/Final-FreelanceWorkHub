package vn.codegym.freelanceworkhub.service;

import vn.codegym.freelanceworkhub.model.SavedJob;

import java.util.List;

public interface SavedJobService {
    SavedJob saveJob(Long jobId, Long freelancerId);

    void removeSavedJob(Long jobId, Long freelancerId);

    List<SavedJob> findByFreelancer(Long freelancerId);
}
