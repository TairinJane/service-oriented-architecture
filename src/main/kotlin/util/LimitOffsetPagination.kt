package util

import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort


class LimitOffsetPagination(private val offset: Int = 0, private val limit: Int = 10, private val sort: Sort) :
    Pageable {

    constructor(offset: Int, limit: Int) : this(offset, limit, Sort.unsorted())

    override fun getPageNumber(): Int {
        return offset / pageSize
    }

    override fun getPageSize(): Int {
        return limit
    }

    override fun getOffset(): Long {
        return offset.toLong()
    }

    override fun getSort(): Sort {
        return sort
    }

    override operator fun next(): Pageable {
        return LimitOffsetPagination(offset + pageSize, pageSize, getSort())
    }

    private fun previous(): LimitOffsetPagination {
        return if (hasPrevious()) LimitOffsetPagination(offset - pageSize, pageSize, getSort()) else this
    }

    override fun previousOrFirst(): Pageable {
        return if (hasPrevious()) previous() else first()
    }

    override fun first(): Pageable {
        return LimitOffsetPagination(0, pageSize, getSort())
    }

    override fun hasPrevious(): Boolean {
        return offset > pageSize
    }
}