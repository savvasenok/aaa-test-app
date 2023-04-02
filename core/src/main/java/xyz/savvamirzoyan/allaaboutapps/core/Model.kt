package xyz.savvamirzoyan.allaaboutapps.core

/**
 * Interface to differentiate models on different layers. E.g domain layer specifies methods, that use only domain-type
 * models, which blocks possibility to pass some data-type model from repository to it
 * */
sealed interface Model {

    /**
     * Created for UI states and models
     * */
    interface UI : Model

    /**
     * Models implementing this interface have to hold only domain information
     * */
    interface Domain : Model

    /**
     * Data-type models used in repository
     * */
    sealed interface Data : Model {

        /**
         * For local storage like database or preferences only
         * */
        interface Local : Data

        /**
         * For API models*/
        interface Cloud : Data
    }
}