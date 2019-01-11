package com.le.aestroider.data

import com.le.aestroider.data.network.NetworkRepository
import javax.inject.Inject

/**
 * <p>
 * Facade for data operations, a.k.a repository pattern. This class is a central point for
 * viewmodels to perform data operations without knowing how the underlying operation is performed.
 * </p>
 *
 * @author Usman
 */
class AestroiderRepository @Inject constructor(private val networkRepository: NetworkRepository) {
}