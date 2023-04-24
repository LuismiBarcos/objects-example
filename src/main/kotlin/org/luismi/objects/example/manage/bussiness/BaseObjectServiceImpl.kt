package org.luismi.objects.example.manage.bussiness

import org.luismi.objects.example.manage.contracts.BaseObjectService
import org.luismi.objects.example.http.contracts.Invoker
import org.luismi.objects.example.template.parser.contracts.Parser
import org.sdi.annotations.Inject

abstract class BaseObjectServiceImpl: BaseObjectService {
    @Inject
    protected lateinit var invoker: Invoker
    @Inject
    protected lateinit var parser: Parser

    override fun getResource(resourceName: String): String =
        BaseObjectService::class.java.getResource(resourceName)?.readText() ?: ""
}